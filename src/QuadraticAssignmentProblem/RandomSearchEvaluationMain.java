package QuadraticAssignmentProblem;

import QuadraticAssignmentProblem.GeneticAlgorithm.GenerationResult;
import QuadraticAssignmentProblem.GeneticAlgorithm.SimplyCodedIndividual;
import QuadraticAssignmentProblem.InputDataHandling.DatFileToArrays;
import QuadraticAssignmentProblem.OtherQAPSolutions.RandomSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class RandomSearchEvaluationMain {
    public static void main(String[] args) throws FileNotFoundException {
        int generation_amount = 100;
        int population_size = 1000;
        int random_seed = 1001;
        int average_of = 10;
        String hadxx = "had20";
        String resource_path = "C:\\Uczelnia\\SI\\zad1\\genetic_algorithm_java\\res\\hadXX\\"+hadxx+".dat";
        String result_path = "C:\\Uczelnia\\SI\\zad1\\genetic_algorithm_java\\res\\"
                +"RandomSearch_"
                +hadxx
                +"_pop"+Integer.toString(population_size)
                +"_gen"+Integer.toString(generation_amount)
                +".csv";
        String sb = "gen" +
                ',' +
                "best"  +
                "optimal_val\n" ;

        PrintWriter pw = new PrintWriter(new File(result_path));
        pw.write(sb);


        DatFileToArrays arrays = new DatFileToArrays(resource_path);
        double[][] distances = arrays.getDistance();
        double[][] weights = arrays.getWeigths();
        SimplyCodedIndividual.setInners(distances, weights);

        int[] optimal_arr = {8,15,16,14,19,6,7,17,1,12,10,11,5,20,2,3,4,9,18,13};

        for(int i = 0; i< optimal_arr.length;i++)
        {
            optimal_arr[i] = optimal_arr[i]-1;
        }
        SimplyCodedIndividual optimal = new SimplyCodedIndividual(optimal_arr);
        double optimal_val = optimal.validationResult();

        int[] rs_best;
        double rs_best_score;
        RandomSearch rs;
        System.out.println("Random Search: ");
        for (int i = 1; i <= generation_amount; i++) {
            rs_best_score = 0;
            for(int pass = 0; pass < average_of; pass++) {
                rs = new RandomSearch(population_size * i, random_seed+pass, weights, distances);
                rs_best = rs.getBest();
                rs_best_score += rs.score(rs_best);
            }
            sb = Integer.toString(i - 1) +
                    ',' +
                    Double.toString(rs_best_score) +
                    ',' +
                    Double.toString(optimal_val) +
                    '\n';
            pw.write(sb);
        }
        pw.close();
    }
}
