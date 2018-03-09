package QuadraticAssignmentProblem.InputDataHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DatFileToArrays {
    private double[][] distance;
    private double[][] weigths;
    private int size;
    public DatFileToArrays(String input_path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(input_path));
        this.size = scanner.nextInt();
        this.distance = new double[size][size];
        this.weigths = new double[size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                distance[i][j] = scanner.nextDouble();
            }
        }
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                weigths[i][j] = scanner.nextDouble();
            }
        }
        scanner.close();
    }

    public double[][] getWeigths() {
        return weigths;
    }

    public int getSize() {
        return size;
    }

    public double[][] getDistance() {
        return distance;
    }
}
