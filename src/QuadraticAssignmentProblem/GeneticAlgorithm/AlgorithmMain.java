package QuadraticAssignmentProblem.GeneticAlgorithm;

import QuadraticAssignmentProblem.InputDataHandling.DatFileToArrays;

import javax.swing.*;
import java.io.FileNotFoundException;

public class AlgorithmMain {
    public static void main(String[] args) throws FileNotFoundException {
        int generation_amount = 30;
        int population_size = 10000;
        int random_seed = 101;
        String resource_path = "C:\\Uczelnia\\SI\\zad1\\genetic_algorithm_java\\res\\hadXX\\had18.dat";
        DatFileToArrays arrays = new DatFileToArrays(resource_path);
        double[][] distances = arrays.getDistance();
        double[][]weights = arrays.getWeigths();

        Algorithm al = new Algorithm(distances, weights, population_size, random_seed);
        GenerationResult[] results = new GenerationResult[generation_amount];

        for(int i = 0; i < generation_amount; i++)
            results[i] = al.nextGen();

        for(int i = 0; i < generation_amount; i++){
            System.out.println(
                    "\nGeneration: " + (i+1)
                    +"\n\tTop 30%: " + results[i].getTop30()
                    +"\n\tBest: " + results[i].getBest()
                    +"\n\tMean: " + results[i].getMean()
                    +"\n\tMedian: " + results[i].getMedian()
                    +"\n\tBottom 30%: " + results[i].getBot30()
            );
        }
        int[] optimal_arr = {3,10,11,2,12,5,6,7,8,1,4,9};

        for(int i = 0; i< optimal_arr.length;i++)
        {
            optimal_arr[i] = optimal_arr[i]-1;
        }

        SimplyCodedIndividual.setInners(distances, weights);
        SimplyCodedIndividual optimal = new SimplyCodedIndividual(optimal_arr);
        System.out.println(optimal.validationResult());

    }
}
