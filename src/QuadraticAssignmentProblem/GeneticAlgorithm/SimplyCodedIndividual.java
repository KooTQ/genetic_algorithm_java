package QuadraticAssignmentProblem.GeneticAlgorithm;

import java.util.Arrays;

public class SimplyCodedIndividual implements  Individual, Comparable<Individual>{
    private double validationResult;
//    private double mutationProbability;
    private boolean isValidated;
    private int[] chromosome;
    private static double[][] weights;
    private static double[][] distances;

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
        double[][] permuted_weights = new double[chromosome.length][chromosome.length];

        for(int i = 0; i < chromosome.length; i++){
            for(int j = 0; j < chromosome.length; j++) {
                permuted_weights[i][j] = weights[chromosome[i]][chromosome[j]];
            }
        }
        double result = 0;
        for(int i = 0; i < chromosome.length; i++){
            for(int j = 0; j < chromosome.length; j++) {
                result += permuted_weights[i][j] * distances[i][j];
            }
        }

        return result;
    }

    @Override
    public int compareTo(Individual o) {
        return (int)(o.validationResult() - this.validationResult());
    }

    public int[] getChromosome(){
        return Arrays.copyOf(chromosome, chromosome.length);
    }
}
