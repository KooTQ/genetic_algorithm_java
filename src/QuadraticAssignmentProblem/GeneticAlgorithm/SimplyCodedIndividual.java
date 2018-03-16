package QuadraticAssignmentProblem.GeneticAlgorithm;

import java.util.Arrays;
import java.util.Random;

public class SimplyCodedIndividual implements  Individual, Comparable<Individual>{
    private double validationResult;
//    private double mutationProbability;
    private boolean isValidated;
    private int[] chromosome;
    private static double[][] weights;
    private static double[][] distances;
    private static int counter=0;

    public SimplyCodedIndividual(int[] chromosome/*, double mutationProbability*/){
        this.chromosome = new int[chromosome.length];
        this.chromosome = Arrays.copyOf(chromosome, chromosome.length);
//        this.mutationProbability = mutationProbability;
        isValidated = false;
    }
    public static void setInners(double[][] distances, double[][] weights){
        SimplyCodedIndividual.distances = Algorithm.deepCopy(distances);
        SimplyCodedIndividual.weights = Algorithm.deepCopy(weights);
    }
    @Override
    public boolean isValidated() {
        return isValidated;
    }

    @Override
    public double validationResult() {
        if(!isValidated) {
            validationResult = calcLoss();
            isValidated = true;
        }
        return validationResult;
    }

    private double calcLoss() {
        double result = 0;
        for(int i = 0; i < chromosome.length; i++){
            for(int j = 0; j < chromosome.length; j++) {
                result += weights[chromosome[i]][chromosome[j]] * distances[i][j];
            }
        }

        return result;
    }
    public void mutate(double mutationProbability){
        Random random = new Random();
        int swap_index;
        for(int i = 0; i < chromosome.length;i++){
            if(random.nextDouble() <= mutationProbability){
                swap_index = random.nextInt(chromosome.length) + i;
                if(swap_index >= chromosome.length){
                    swap_index -= chromosome.length;
                }
                swap(i, swap_index);
            }
        }
    }
    private void swap(int from, int to){
        int temp = chromosome[from];
        chromosome[from] = chromosome[to];
        chromosome[to] = temp;
    }

    @Override
    public int compareTo(Individual o) {
        return (int)(this.validationResult() - o.validationResult());
    }

    public int[] getChromosome(){
        return Arrays.copyOf(chromosome, chromosome.length);
    }
}
