package main.GeneticAlgorithm;

import java.util.Arrays;
import java.util.Random;

public class Algorithm {

    private double[][] distances;
    private double[][] weights;
    private int populationSize;
    private long randomSeed;
    public int[][] individuals;
    private int individualGenomeSize;

    public Algorithm(double[][] distances, double[][] weights, int populationSize, long randomSeed){
        this.distances = deepCopy(distances);
        this.weights = deepCopy(weights);

        this.populationSize = populationSize;
        this.randomSeed = randomSeed;
        this.individualGenomeSize = weights[0].length;
        populate(this.populationSize);
    }

    private void populate(int amountToPopulate) {
        if(amountToPopulate>populationSize)
            throw new IllegalArgumentException("Cannot populate more than populationSize!");
        individuals = new int[populationSize][individualGenomeSize];
        Random random = new Random(randomSeed);
        for(int i = 0; i < amountToPopulate; i++){
            individuals[i] = generateGenome(random);
        }
    }
//    private boolean anyIsTrue(boolean[] truth_table){
//        boolean result = false;
//        for(int i = 0; i<truth_table.length && !result; i++)
//            result = result || truth_table[i];
//        return result;
//    }
    private int[] generateGenome(Random random){
        int[] genome = new int[individualGenomeSize];
        boolean[] genome_places_used;
        genome_places_used = new boolean[individualGenomeSize];
        for(int j=0;j<individualGenomeSize;j++){
            genome_places_used[j] = false;
        }
        int set_numbers = 0;
        while(set_numbers < individualGenomeSize){
            int place = random.nextInt(individualGenomeSize);
            if(!genome_places_used[place]){
                genome_places_used[place] = true;
                genome[place] = set_numbers;
                set_numbers++;
            }
            else{
                boolean flag = false;
                for(int k = 0; k < individualGenomeSize && !flag; k++){
                    if(!genome_places_used[k]){
                        genome_places_used[k] = true;
                        genome[k] = set_numbers;
                        set_numbers++;
                        flag = true;
                    }
                }
            }
        }
        return genome;
    }
    private static double[][] deepCopy(double[][] original) {
        if (original == null) {
            return null;
        }

        final double[][] result = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    public double calcLoss(int[] permutation) {
        double[][] permuted_weights = new double[permutation.length][permutation.length];

        for(int i = 0; i < permutation.length; i++){
            for(int j = 0; j < permutation.length; j++) {
                permuted_weights[i][j] = weights[permutation[i]][permutation[j]];
            }
        }
        double result = 0;
        for(int i = 0; i < permutation.length; i++){
            for(int j = 0; j < permutation.length; j++) {
                result += permuted_weights[i][j] * distances[i][j];
            }
        }

        return result;
    }

}
