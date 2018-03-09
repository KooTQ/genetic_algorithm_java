package QuadraticAssignmentProblem.GeneticAlgorithm;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Random;

public class Algorithm {

    private double[][] distances;
    private double[][] weights;
    private int populationSize;
    private long randomSeed;
    public SimplyCodedIndividual[] individuals;
    private int individualGenomeSize;
    private Random random;

    public Algorithm(double[][] distances, double[][] weights, int populationSize, long randomSeed){
        this.distances = deepCopy(distances);
        this.weights = deepCopy(weights);

        this.populationSize = populationSize;
        this.randomSeed = randomSeed;
        this.individualGenomeSize = weights[0].length;
        this.random = new Random(this.randomSeed);
        SimplyCodedIndividual.setInners(this.distances, this.weights);
        populate(this.populationSize);
    }

    public GenerationResult nextGen(){
//        System.out.println("Generated");
//        printPopulation(individuals);
        Arrays.sort(individuals);
        System.out.println("Sorted");
        printPopulation(individuals);

        SimplyCodedIndividual[] chosen_ones = roulette();
//        System.out.println("Chosen ones");
//        printPopulation(chosen_ones);

        SimplyCodedIndividual[] new_invids = crossover0X(chosen_ones);

        System.arraycopy(new_invids, 0, individuals, 0, new_invids.length);
        return new GenerationResult(0,0,0,0,0);
    }

    private SimplyCodedIndividual[] crossover0X(SimplyCodedIndividual[] chosen_ones) {
        SimplyCodedIndividual[] result = new SimplyCodedIndividual[populationSize];
        SimplyCodedIndividual first, second;
        result[0] = individuals[individuals.length-1];
        for(int i = 1; i< populationSize; i+=2){
            first = chosen_ones[i];
            second = chosen_ones[i-1];
            result[i] = crossOX_inner(first, second);
            if(i+1 <populationSize)
                result[i+1] = crossOX_inner(second, first);
        }
        return result;
    }

    public SimplyCodedIndividual crossOX_inner(SimplyCodedIndividual first, SimplyCodedIndividual second) {
        int[] chromosome = new int[individualGenomeSize];
        int i;
        for(i = 0; i < individualGenomeSize/2.0; i++) {
            chromosome[i] = first.getChromosome()[i];
        }
        for(int j = i;j<individualGenomeSize;j++){
            chromosome[j] = -1;
        }
        boolean contain;
        for(int j = 0; j< individualGenomeSize && i<individualGenomeSize; j++){
            contain = contains(chromosome, second.getChromosome()[j], i);
            if(!contain) {
                chromosome[i] = second.getChromosome()[j];
                i++;
            }
        }
        return new SimplyCodedIndividual(chromosome);
    }

    private boolean contains(int[] chromosome, int value, int max) {
        boolean result = false;
        for(int i = 0; i<=max && !result; i++){
            if(chromosome[i] == value) {
                result = true;
            }
        }
        return result;
    }

    private SimplyCodedIndividual[] roulette() {
        double sum_of_points = 0;
        double upper_sum;
        double top_value = individuals[0].validationResult() + 2;
        Roulette[] all_of_inds = new Roulette[populationSize];
        for(int i = 0; i < individuals.length; i++){
            upper_sum = sum_of_points + (top_value - individuals[i].validationResult());
            all_of_inds[i] = new Roulette(individuals[i], sum_of_points, upper_sum);
            sum_of_points = upper_sum;
        }
        Random rand = new Random();
        SimplyCodedIndividual[] chosen_ones = new SimplyCodedIndividual[populationSize];

        for(int i = 0; i < chosen_ones.length;i++){
            double value = rand.nextDouble()*sum_of_points;
            chosen_ones[i] = choose(all_of_inds, value);
        }

        return chosen_ones;
    }

    public void printPopulation(SimplyCodedIndividual[] chosen_ones) {
        for(SimplyCodedIndividual in_array: chosen_ones)
        {
            System.out.print("[");
            for(int item:in_array.getChromosome())
            {
                System.out.print(item + ", ");
            }
            System.out.println("---] \t \t" + in_array.validationResult());
        }
        System.out.println();
        System.out.println();
    }

    private SimplyCodedIndividual choose(Roulette[] all_of_inds, double value) {
        SimplyCodedIndividual result = null;
        for(int i = 0; i < all_of_inds.length && result == null; i++){
            if(all_of_inds[i].upper >= value){
                result = all_of_inds[i].s;
            }
        }
        return  result;
    }

    private void populate(int amountToPopulate) {
        if(amountToPopulate>populationSize)
            throw new IllegalArgumentException("Cannot populate more than populationSize!");
        individuals = new SimplyCodedIndividual[populationSize];
        for(int i = 0; i < amountToPopulate; i++){
            individuals[i] = new SimplyCodedIndividual(generateGenome(random));
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

    public static double[][] deepCopy(double[][] original) {
        if (original == null) {
            return null;
        }

        final double[][] result = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
}
class Roulette{
    SimplyCodedIndividual s;
    double lower;
    double upper;

    Roulette(SimplyCodedIndividual s, double lower, double upper){
        this.s = s;
        this.lower = lower;
        this.upper = upper;
    }
}
