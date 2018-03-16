package QuadraticAssignmentProblem.OtherQAPSolutions;

import QuadraticAssignmentProblem.GeneticAlgorithm.SimplyCodedIndividual;

import java.util.Random;

import static QuadraticAssignmentProblem.GeneticAlgorithm.Algorithm.deepCopy;

public class RandomSearch {
    private int populationSize;
    private int[][] individuals;
    private double[][] weights;
    private double[][] distances;
    private Random random;
    private int individualGenomeSize;

    public RandomSearch(int populationSize,int randomSeed, double[][] weights, double[][]distances){
        this.random = new Random(randomSeed);
        this.populationSize = populationSize;
        this.individualGenomeSize = weights.length;
        this.distances = deepCopy(distances);
        this.weights = deepCopy(weights);
        populate();
    }

    private void populate() {
        individuals = new int[populationSize][];
        for(int i = 0; i < populationSize; i++){
            individuals[i] = generateGenome(random, individualGenomeSize);
        }
    }

    private int[] generateGenome(Random random, int individualGenomeSize){
        return getIGenome(random, individualGenomeSize);
    }

    public static int[] getIGenome(Random random, int individualGenomeSize) {
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

    public int[] getBest(){
        int[] best = individuals[0];
        double best_score = score(individuals[0]);
        double new_score;
        for(int i = 1; i < individuals.length; i++){
            new_score = score(individuals[i]);
            if(new_score < best_score){
                best = individuals[i];
                best_score = new_score;
            }
        }

        return best;
    }

    public double score(int[] individual) {
        double result = 0;
        for(int i = 0; i < individual.length; i++){
            for(int j = 0; j < individual.length; j++) {
                result += weights[individual[i]][individual[j]] * distances[i][j];
            }
        }
        return result;
    }

}
