package QuadraticAssignmentProblem.GeneticAlgorithm;

import java.util.Arrays;
import java.util.Random;

import static QuadraticAssignmentProblem.OtherQAPSolutions.RandomSearch.getIGenome;

public class Algorithm {

    private double[][] distances;
    private double[][] weights;
    private int populationSize;
    private long randomSeed;
    public SimplyCodedIndividual[] individuals;
    private int individualGenomeSize;
    private Random random;
    private int keepBest;
    private double crossingProbability;
    private double mutationProbability;

    public Algorithm(double[][] distances, double[][] weights, int populationSize, long randomSeed,
                     double keepBest, double crossingProbability, double mutationProbability){
        this.distances = deepCopy(distances);
        this.weights = deepCopy(weights);

        this.populationSize = populationSize;
        this.randomSeed = randomSeed;
        this.individualGenomeSize = weights[0].length;
        this.random = new Random(this.randomSeed);
        SimplyCodedIndividual.setInners(this.distances, this.weights);
        populate(this.populationSize);
        Arrays.sort(individuals);
        if(crossingProbability >= 0 && crossingProbability <=1.0){
            this.crossingProbability = crossingProbability;
        }
        else {
            this.crossingProbability = 0;
        }
        if(mutationProbability >= 0 && mutationProbability <=1.0){
            this.mutationProbability = mutationProbability;
        }
        else {
            this.mutationProbability = 0;
        }
        if(keepBest >= 0 && keepBest <=1.0){
            this.keepBest = (int)keepBest*populationSize;
        }
        else {
            this.keepBest = 0;
        }
    }

    public GenerationResult nextGen(){

        SimplyCodedIndividual[] chosen_ones = roulette();

        SimplyCodedIndividual[] new_invids = crossover0X(chosen_ones);

        System.arraycopy(new_invids, 0, individuals, 0, new_invids.length);
        Arrays.sort(individuals);
        double best  = individuals[0].validationResult();
        double median;
        if(individuals.length %2 == 0){
            median = individuals[populationSize/2].validationResult()/2;
            median += individuals[populationSize/2+1].validationResult()/2;
        }
        else
            median = individuals[populationSize/2+1].validationResult();
        double mean = 0;
        double top30 = 0;
        double bot30 = 0;
        int index_top30 = (int)(populationSize * 0.3);
        int index_bot30 = (int)(populationSize * 0.7);
        for(int i = 0; i < index_top30; i++){
            top30 += individuals[i].validationResult()/index_top30;
            mean += individuals[i].validationResult()/populationSize;
        }
        for(int i = index_top30; i < index_bot30; i++){
            mean += individuals[i].validationResult()/populationSize;
        }
        for(int i = index_bot30; i < populationSize; i++){
            bot30 += individuals[i].validationResult()/index_top30;
            mean += individuals[i].validationResult()/populationSize;
        }

        return new GenerationResult(mean, median, best, top30, bot30);
    }

    private SimplyCodedIndividual[] crossover0X(SimplyCodedIndividual[] chosen_ones) {
        SimplyCodedIndividual[] result = new SimplyCodedIndividual[populationSize];
        int i=0;
        SimplyCodedIndividual first, second;
        for(;i <= keepBest; i++) {
            result[0] = individuals[0];
        }
        Random crossing_random = new Random(randomSeed);
        for(; i< populationSize; i+=2){
            first = chosen_ones[i];
            second = chosen_ones[i-1];

            if(crossing_random.nextDouble() <= crossingProbability) {
                result[i] = crossOX_inner(first, second);
                if(i+1 <populationSize)
                    result[i+1] = crossOX_inner(second, first);
            }
            else{
                result[i] = first;
                if(i+1 <populationSize)
                    result[i+1] = second;
            }
        }
        return result;
    }

    public SimplyCodedIndividual crossOX_inner(SimplyCodedIndividual first, SimplyCodedIndividual second) {
        int[] chromosome = new int[individualGenomeSize];
        int i;
        SimplyCodedIndividual result;
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
        result = new SimplyCodedIndividual(chromosome);
        if(mutationProbability > 0.0){
            result.mutate(mutationProbability);
        }
        return result;
    }

    public static boolean contains(int[] chromosome, int value, int max) {
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
        double top_value = individuals[populationSize-1].validationResult();
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
            System.out.println("] \t \t" + in_array.validationResult());
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
        return getIGenome(random, individualGenomeSize);
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
