import java.util.*;

public class Dimension {
    private String name;
    private int coefficient;
    private List<Metric> metrics = new ArrayList<>();

    public Dimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }

    public void addMetric(Metric m) {
        metrics.add(m);
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public String getName() {
        return name;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public double calculateScore() {
        double totalWeightedScore = 0;
        double totalCoefficient = 0;
        for (Metric m : metrics) {
            totalWeightedScore += m.calculateScore() * m.getCoefficient();
            totalCoefficient += m.getCoefficient();
        }
        return totalCoefficient == 0 ? 0 : totalWeightedScore / totalCoefficient;
    }
}