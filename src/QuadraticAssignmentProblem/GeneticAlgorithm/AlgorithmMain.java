package QuadraticAssignmentProblem.GeneticAlgorithm;

import javax.swing.*;

public class AlgorithmMain {
    public static void main(String[] args) {
//        double[][] distances = {
//                {0, 32,	68,	97,	75,	70,	75,	40,	24},
//                {32,    0,	42,	80,	53,	65,	82,	47,	29},
//                {68,    42, 0,	45,	15,	49,	79,	55,	50},
//                {97,	80,	45,	0,	30,	36,	65,	65,	73},
//                {75,	53,	15,	30,	0,	38,	69,	53,	53},
//                {70,	65,	49,	36,	38,	0,	31,	32,	46},
//                {75,    82, 79, 65, 69, 31, 0, 36,	56},
//                {40,    47, 55, 65, 53, 32, 36, 0,	19},
//                {24,    29, 50, 73, 53, 46, 56, 19,0}
//        };
//        double[][] weights = {
//                {0,	2,	4,	0,	0,	0,	2,	0,	0},
//                {2,	0,	3,	1,	0,	6,	0,	0,	2},
//                {4,	3,	0,	0,	0,	3,	0,	0,	0},
//                {0,	1,	0,	0,	1,	0,	1,	2,	0},
//                {0,	0,	0,	1,	0,	0,	0,	0,	0},
//                {0,	6,	3,	0,	0,	0,	0,	0,	2},
//                {2,	0,	0,	1,	0,	0,	0,	4,	3},
//                {0,	0,	0,	2,	0,	0,	4,	0,	0},
//                {0,	2,	0,	0,	0,	2,	3,	0,	0}
//        };

        double[][] distances = {
                {0,  1,  2,  2,  3,  4,  4,  5,  3,  5,  6,  7},
                {1,  0,  1,  1,  2,  3 , 3,  4, 2,  4,  5,  6},
                {2,  1,  0,  2,  1,  2,  2,  3,  1,  3,  4,  5},
                {2,  1,  2,  0,  1,  2 , 2, 3,  3,  3,  4,  5},
                {3 , 2,  1,  1,  0,  1,  1,  2,  2,  2,  3,  4},
                {4,  3,  2,  2,  1,  0,  2,  3,  3,  1,  2,  3},
                {4,  3,  2,  2,  1,  2,  0,  1,  3,  1,  2,  3},
                {5,  4,  3,  3 , 2 , 3 , 1 , 0 , 4 , 2 , 1,  2},
                {3,  2,  1 , 3 , 2 , 3 , 3 , 4 , 0 , 4 , 5,  6},
                {5,  4,  3,  3 , 2 , 1 , 1 , 2 , 4 , 0 , 1,  2},
                {6,  5,  4,  4 , 3 , 2 , 2 , 1 , 5 , 1 , 0,  1},
                {7,  6,  5,  5 , 4 , 3 , 3 , 2 , 6 , 2 , 1,  0}
        };
        double[][] weights = {
                {0,  3,  4,  6,  8,  5,  6,  6,  5,  1,  4,  6},
                {3,  0,  6,  3,  7,  9,  9,  2,  2,  7,  4,  7},
                {4,  6,  0,  2,  6,  4,  4,  4,  2,  6,  3,  6},
                {6,  3,  2,  0,  5 , 5,  3 , 3,  9,  4,  3,  6},
                {8,  7,  6,  5,  0,  4,  3  ,4,  5,  7,  6,  7},
                {5,  9,  4,  5,  4,  0,  8,  5,  5,  5,  7,  5},
                {6,  9,  4,  3,  3,  8,  0 , 6,  8,  4,  6,  7},
                {6,  2,  4,  3,  4 , 5,  6  ,0,  1,  5,  5 , 3},
                {5,  2 , 2,  9,  5,  5,  8,  1,  0,  4,  5,  2},
                {1,  7,  6,  4,  7,  5,  4 , 5,  4,  0,  7,  7},
                {4 , 4,  3,  3,  6,  7,  6  ,5,  5,  7,  0,  9},
                {6,  7,  6,  6,  7,  5,  7 , 3,  2,  7,  9,  0}
        };
        int generation_amount = 30;

        Algorithm al = new Algorithm(distances, weights, 10000, 101);

//        int[] permutation1 = {1, 2, 0, 3, 4, 5, 6, 7, 8};
//        int[] permutation2 = {8, 1, 2, 3, 0, 4, 5, 6, 7};
//        SimplyCodedIndividual sci = al.crossOX_inner(
//                new SimplyCodedIndividual(permutation1),
//                new SimplyCodedIndividual(permutation2)
//        );
//        GenerationResult[] results = new GenerationResult[generation_amount];
//        for(int i = 0; i < generation_amount; i++)
//            results[i] = al.nextGen();
        int[] optimal_arr = {3,10,11,2,12,5,6,7,8,1,4,9};

        for(int i = 0; i< optimal_arr.length;i++)
        {
            optimal_arr[i] = optimal_arr[i]-1;
        }

        SimplyCodedIndividual.setInners(distances, weights);
        SimplyCodedIndividual optimal = new SimplyCodedIndividual(optimal_arr);
        System.out.println(optimal.validationResult());
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
//        SimplyCodedIndividual[] res = al.individuals;
//        for(SimplyCodedIndividual in_array: res)
//        {
//            System.out.print("[");
//            for(int item:in_array.getChromosome())
//            {
//                System.out.print(item + ", ");
//            }
//            System.out.println("] \t \t" + in_array.validationResult());
//        }

    }
}
