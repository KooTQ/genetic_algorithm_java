package QuadraticAssignmentProblem.GeneticAlgorithm;

public class GenerationResult {
    private double mean;
    private double median;
    private double best;
    private double top30;
    private double bot30;

    GenerationResult(double mean, double median, double best, double top30, double bot30){
        this.mean = mean;
        this.median = median;
        this.best = best;
        this.top30 = top30;
        this.bot30 = bot30;
    }

    public double getBot30() {
        return bot30;
    }

    public double getTop30() {

        return top30;
    }

    public double getBest() {

        return best;
    }

    public double getMedian() {

        return median;
    }

    public double getMean() {

        return mean;
    }
}
