package QuadraticAssignmentProblem.GeneticAlgorithm;

public class SimplyCodedIndividual implements  Individual{
    private double validationResult;
    private double mutationProbability;
    private boolean isValidated;
    private int[] chromosome;

    public SimplyCodedIndividual( int[] chromosome, double mutationProbability){
        this.chromosome = new int[chromosome.length];
        System.arraycopy(chromosome, 0, this.chromosome, 0, chromosome.length);
        this.mutationProbability = mutationProbability;
        isValidated = false;
    }
    
    @Override
    public boolean isValidated() {
        return isValidated;
    }

    @Override
    public double validationResult() {
        return validationResult;
    }

}
