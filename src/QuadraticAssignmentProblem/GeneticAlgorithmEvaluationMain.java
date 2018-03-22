package QuadraticAssignmentProblem;

import QuadraticAssignmentProblem.GeneticAlgorithm.Algorithm;
import QuadraticAssignmentProblem.GeneticAlgorithm.GenerationResult;
import QuadraticAssignmentProblem.GeneticAlgorithm.SimplyCodedIndividual;
import QuadraticAssignmentProblem.InputDataHandling.DatFileToArrays;
import QuadraticAssignmentProblem.OtherQAPSolutions.GreedySearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class GeneticAlgorithmEvaluationMain {
    public static void main(String[] args) throws FileNotFoundException {
        //3 10 30 50 100 500 1000 10000
        int generation_amount = 10000;
        //3 10 30 50 100 500 1000 10000
        int population_size = 100;
        int random_seed = 1001;
        int average_of = 10;
        double keep_best = 0;
        double crossing_probability = 0.93;
        double individual_mutation_probability = 0.007;
        String hadxx = "had20";
        String resource_path = "C:\\Uczelnia\\SI\\zad1\\genetic_algorithm_java\\res\\hadXX\\"+hadxx+".dat";
        String result_path = "C:\\Uczelnia\\SI\\zad1\\genetic_algorithm_java\\res\\"
                +hadxx
                +"_pop"+Integer.toString(population_size)
                +"_gen"+Integer.toString(generation_amount)
                +"_keep_best"+Double.toString(keep_best)
                +"_cross_prob"+Double.toString(crossing_probability)
                +"_mut_prob"+Double.toString(individual_mutation_probability)
                +".csv";
        GenerationResult[] results;

        DatFileToArrays arrays = new DatFileToArrays(resource_path);
        double[][] distances = arrays.getDistance();
        double[][] weights = arrays.getWeigths();

        Algorithm al = new Algorithm(distances, weights, population_size, random_seed, keep_best,
                crossing_probability, individual_mutation_probability);
        results = new GenerationResult[generation_amount];

        for(int i = 0; i < generation_amount; i++)
            results[i] = al.nextGen();

        for(int pass = 1; pass < average_of; pass++){
            al = new Algorithm(distances, weights, population_size, random_seed+pass, keep_best,
                    crossing_probability, individual_mutation_probability);

            for(int i = 0; i < generation_amount; i++)
                results[i].add(al.nextGen());
        }
        System.out.println("Optimal: ");
        int[] optimal_arr = {8,15,16,14,19,6,7,17,1,12,10,11,5,20,2,3,4,9,18,13};

        for(int i = 0; i< optimal_arr.length;i++)
        {
            optimal_arr[i] = optimal_arr[i]-1;
        }
        SimplyCodedIndividual optimal = new SimplyCodedIndividual(optimal_arr);
        System.out.println(Arrays.toString(optimal_arr));
        double optimal_val = optimal.validationResult();
        System.out.println(optimal_val);

        PrintWriter pw = new PrintWriter(new File(result_path));
        String sb = "gen" +
                ',' +
                "best" +
                ',' +
                "mean" +
                ',' +
                "median" +
                ',' +
                "top 30%" +
                ',' +
                "bot 30%," +
                "optimal_val\n" ;
        pw.write(sb);
        for(int i = 0; i < generation_amount; i++){
//            System.out.println(
//                    "\nGeneration: " + (i+1)
//                    +"\n\tBest: " + results[i].getBest()
//                    +"\n\tMean: " + results[i].getMean()
//                    +"\n\tMedian: " + results[i].getMedian() +"\n\tTop 30%: " + results[i].getTop30()
//                    +"\n\tBottom 30%: " + results[i].getBot30()
//
//            );
            sb = Integer.toString(i) +
                    ',' +
                    Double.toString(results[i].getBest()/average_of) +
                    ',' +
                    Double.toString(results[i].getMean()/average_of)+
                    ',' +
                    Double.toString(results[i].getMedian()/average_of) +
                    ',' +
                    Double.toString(results[i].getTop30()/average_of) +
                    ',' +
                    Double.toString(results[i].getBot30()/average_of) +
                    ',' +
                    Double.toString(optimal_val) +
                    '\n' ;
            pw.write(sb);

        }
        pw.close();
        System.out.println("Genetic Algorithm: ");
        System.out.println(Arrays.toString(al.individuals[0].getChromosome())+"\n"+al.individuals[0].validationResult());

        System.out.println("Greedy Search: ");
        GreedySearch gs = new GreedySearch(weights, distances);
        System.out.println(Arrays.toString(gs.getGreediestIndividual()));
        System.out.println(gs.getGreediestValue());


    }
}
