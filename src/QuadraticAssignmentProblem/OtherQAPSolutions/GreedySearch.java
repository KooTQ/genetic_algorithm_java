package QuadraticAssignmentProblem.OtherQAPSolutions;

import static QuadraticAssignmentProblem.GeneticAlgorithm.Algorithm.contains;
import static QuadraticAssignmentProblem.GeneticAlgorithm.Algorithm.deepCopy;

import java.util.Arrays;

public class GreedySearch {
    private int individualSize;
    private double[][] weights;
    private double[][] distances;
    private int[] greediestIndividual;
    private double greediestValue;

    public GreedySearch(double[][] weights, double[][] distances){
        this.weights = deepCopy(weights);
        this.distances = deepCopy(distances);
        this.individualSize = distances.length;
        this.greediestIndividual = new int[this.individualSize];
        for(int i = 0; i < greediestIndividual.length; i++){
            this.greediestIndividual[i] = -1;
        }

        search();
    }

    private void search() {
        int best_index1 = 0;
        int best_index2 = 0;
        double best_value = Double.MAX_VALUE;
        double current_value;
        for(int i = 0 ; i < individualSize; i++){
            for(int j = 0; j < individualSize; j++){
                if(i!=j){
                    current_value = calc_greedy_short_distance(i,j);
                    if(best_value > current_value){
                        best_index1 = i;
                        best_index2 = j;
                        best_value = current_value;
                    }
                }
            }
        }
        greediestIndividual[0] = best_index1;
        greediestIndividual[1] = best_index2;
        for(int i = 2; i < individualSize; i++){
            best_value = Double.MAX_VALUE;
            for(int j = 0; j < individualSize; j++) {
                if (!contains(greediestIndividual, j, i)){
                    current_value = calc_greedy_distance(i, j);
                    if(current_value < best_value){
                        best_index1 = j;
                        best_value = current_value;
                    }
                }
            }
            greediestIndividual[i] = best_index1;
        }
        greediestValue = score();
    }

    private double calc_greedy_short_distance(int i, int j) {

        return weights[i][j]*distances[i][j] + weights[j][i]*distances[j][i] ;
    }
    private double calc_greedy_distance(int maxSize, int index){
        double result = 0;
        for(int i = 0; i < maxSize; i++){
            result += weights[this.greediestIndividual[i]][index]*distances[i][index];
            result += weights[index][this.greediestIndividual[i]]*distances[index][i];
        }
        return result;
    }
    public int[] getGreediestIndividual(){
        return Arrays.copyOf(greediestIndividual, greediestIndividual.length);
    }

    public double getGreediestValue(){
        return greediestValue;
    }

    private double score() {
        double result = 0;
        for(int i = 0; i < individualSize; i++){
            for(int j = 0; j < individualSize; j++) {
                result += weights[greediestIndividual[i]][greediestIndividual[j]] * distances[i][j];
            }
        }
        return result;
    }
}
