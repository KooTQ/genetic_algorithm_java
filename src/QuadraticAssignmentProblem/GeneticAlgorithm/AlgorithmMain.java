package QuadraticAssignmentProblem.GeneticAlgorithm;

public class AlgorithmMain {
    public static void main(String[] args) {
        double[][] distances = {
                {0,     22, 53, 53},
                {22,    0,  40, 62},
                {53,	40, 0,  55},
                {53,	62,	55,	0}
        };
        double[][] weights = {
                {0,	3,	0,	2},
                {3,	0,	0,	1},
                {0,	0,	0,	4},
                {2,	1,	4,	0}
        };



        int[] permutation = {3, 2, 1, 0};
        Algorithm al = new Algorithm(distances, weights, 100, 101);
        int[][] res = al.individuals;
        for(int[] in_array: res)
        {
            System.out.print("[");
            for(int item:in_array)
            {
                System.out.print(item + ", ");
            }
            System.out.print("]");
            System.out.print(":\t \t");

            System.out.println(al.calcLoss(in_array));

        }

    }
}
