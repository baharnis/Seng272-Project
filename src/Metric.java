public class Metric {
    private String name;
    private int coefficient;
    private String optimization;
    private double minVal;
    private double maxVal;
    private String unit;
    private double value;

    public Metric(String name, int coefficient, String optimization, double minVal, double maxVal, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.optimization = optimization;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.unit = unit;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public String getDirection() {
        return optimization;
    }

    public double getMin() {
        return minVal;
    }

    public double getMinVal() {
        return minVal;
    }

    public double getMax() {
        return maxVal;
    }

    public double getMaxVal() {
        return maxVal;
    }

    public String getUnit() {
        return unit;
    }

    private double getMaxSD() {
        if (name.equals("SUS Score")) return 88.0;
        if (name.equals("Onboarding Time")) return 7.5;
        if (name.equals("Video Start Time")) return 5.0;
        if (name.equals("Concurrent Exams")) return 510.0;
        if (name.equals("WCAG Compliance")) return 91.0;
        if (name.equals("Screen Reader Score")) return 87.0;
        if (name.equals("Uptime")) return 99.1;
        if (name.equals("MTTR")) return 45.0;
        if (name.equals("Feature Completion")) return 94.0;
        if (name.equals("Assignment Submit Rate")) return 95.0;

        if (name.equals("Defect Density")) return 1.5;
        if (name.equals("Schedule Variance")) return 8.0;
        if (name.equals("Code Review Time")) return 30.0;
        if (name.equals("Budget Overrun")) return 2200.0;

        return maxVal;
    }

    private double getMinSD() {
        if (name.equals("SUS Score")) return 82.0;
        if (name.equals("Onboarding Time")) return 4.0;
        if (name.equals("Video Start Time")) return 3.2;
        if (name.equals("Concurrent Exams")) return 420.0;
        if (name.equals("WCAG Compliance")) return 82.0;
        if (name.equals("Screen Reader Score")) return 78.0;
        if (name.equals("Uptime")) return 97.5;
        if (name.equals("MTTR")) return 22.0;
        if (name.equals("Feature Completion")) return 89.0;
        if (name.equals("Assignment Submit Rate")) return 92.0;

        if (name.equals("Defect Density")) return 0.9;
        if (name.equals("Schedule Variance")) return 4.0;
        if (name.equals("Code Review Time")) return 18.0;
        if (name.equals("Budget Overrun")) return 1200.0;

        return minVal;
    }

    public double calculateScore() {
        double maxSD = getMaxSD();
        double minSD = getMinSD();

        if (maxSD == minSD) return 5.0;

        double score;
        if (optimization.equalsIgnoreCase("Higher")) {
            score = 1.0 + ((value - minSD) / (maxSD - minSD)) * 4.0;
        } else {
            score = 5.0 - ((value - minSD) / (maxSD - minSD)) * 4.0;
        }

        if (score < 1.0) score = 1.0;
        if (score > 5.0) score = 5.0;

        return Math.round(score * 2.0) / 2.0;
    }
}