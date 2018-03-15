package QuadraticAssignmentProblem;

import QuadraticAssignmentProblem.GeneticAlgorithm.Algorithm;
import QuadraticAssignmentProblem.GeneticAlgorithm.GenerationResult;
import QuadraticAssignmentProblem.InputDataHandling.DatFileToArrays;
import QuadraticAssignmentProblem.OtherQAPSolutions.RandomSearch;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int generation_amount = 1000;
        int population_size = 10000;
        int random_seed = 1001;
        String resource_path = "C:\\Uczelnia\\SI\\zad1\\genetic_algorithm_java\\res\\hadXX\\had12.dat";
        DatFileToArrays arrays = new DatFileToArrays(resource_path);
        double[][] distances = arrays.getDistance();
        double[][] weights = arrays.getWeigths();

//        Algorithm al = new Algorithm(distances, weights, population_size, random_seed, 0.1,
//                0.93, 0.007);
//        GenerationResult[] results = new GenerationResult[generation_amount];
//
//        for(int i = 0; i < generation_amount; i++)
//            results[i] = al.nextGen();
//
//        for(int i = 0; i < generation_amount; i++){
//            System.out.println(
//                    "\nGeneration: " + (i+1)
//                    +"\n\tTop 30%: " + results[i].getTop30()
//                    +"\n\tBest: " + results[i].getBest()
//                    +"\n\tMean: " + results[i].getMean()
//                    +"\n\tMedian: " + results[i].getMedian()
//                    +"\n\tBottom 30%: " + results[i].getBot30()
//            );
//        }
//        int[] optimal_arr = {3,10,11,2,12,5,6,7,8,1,4,9};
//
//        for(int i = 0; i< optimal_arr.length;i++)
//        {
//            optimal_arr[i] = optimal_arr[i]-1;
//        }
//        System.out.println(Arrays.toString(al.individuals[0].getChromosome())+": "+al.individuals[0].validationResult());
//        SimplyCodedIndividual.setInners(distances, weights);
//        SimplyCodedIndividual optimal = new SimplyCodedIndividual(optimal_arr);
//        System.out.println(optimal.validationResult());
        RandomSearch rs = new RandomSearch(population_size*generation_amount, random_seed, weights, distances);
        int[] rs_best = rs.getBest();
        System.out.println(Arrays.toString(rs_best));
        System.out.println(rs.score(rs_best));
    }
}
