public class MeasurementController {
    private User user;
    private Scenario scenario;

    public void setUser(User user) { this.user = user; }
    public User getUser() { return user; }
    public Scenario getScenario() { return scenario; }

    public void initializeScenario(String mode, String type, String scenarioName) {
        scenario = new Scenario(scenarioName, mode, type);

        if (type.equals("Product Quality")) {
            Dimension d1 = new Dimension("Usability", 50);
            Metric m1 = new Metric("SUS Score", 50, "Higher", 0, 100, "points");
            Metric m2 = new Metric("Onboarding Time", 50, "Lower", 0, 60, "min");

            if (mode.equals("Education")) {
                m1.setValue(82.0);
                m2.setValue(7.5);
            } else {
                m1.setValue(88.0);
                m2.setValue(4.0);
            }
            d1.addMetric(m1);
            d1.addMetric(m2);
            scenario.addDimension(d1);

            Dimension d2 = new Dimension("Performance Efficiency", 50);
            Metric m3 = new Metric("Video Start Time", 50, "Lower", 0, 15, "sec");
            Metric m4 = new Metric("Concurrent Exams", 50, "Higher", 0, 600, "users");

            if (mode.equals("Education")) {
                m3.setValue(3.2);
                m4.setValue(510.0);
            } else {
                m3.setValue(5.0);
                m4.setValue(420.0);
            }
            d2.addMetric(m3);
            d2.addMetric(m4);
            scenario.addDimension(d2);

            Dimension d3 = new Dimension("Accessibility", 50);
            Metric m5 = new Metric("WCAG Compliance", 50, "Higher", 0, 100, "%");
            Metric m6 = new Metric("Screen Reader Score", 50, "Higher", 0, 100, "%");

            if (mode.equals("Education")) {
                m5.setValue(91.0);
                m6.setValue(87.0);
            } else {
                m5.setValue(82.0);
                m6.setValue(78.0);
            }
            d3.addMetric(m5);
            d3.addMetric(m6);
            scenario.addDimension(d3);

            Dimension d4 = new Dimension("Reliability", 50);
            Metric m7 = new Metric("Uptime", 50, "Higher", 95, 100, "%");
            Metric m8 = new Metric("MTTR", 50, "Lower", 0, 120, "min");

            if (mode.equals("Education")) {
                m7.setValue(99.1);
                m8.setValue(22.0);
            } else {
                m7.setValue(97.5);
                m8.setValue(45.0);
            }
            d4.addMetric(m7);
            d4.addMetric(m8);
            scenario.addDimension(d4);

            Dimension d5 = new Dimension("Functional Suitability", 50);
            Metric m9 = new Metric("Feature Completion", 50, "Higher", 0, 100, "%");
            Metric m10 = new Metric("Assignment Submit Rate", 50, "Higher", 0, 100, "%");

            if (mode.equals("Education")) {
                m9.setValue(94.0);
                m10.setValue(92.0);
            } else {
                m9.setValue(89.0);
                m10.setValue(95.0);
            }
            d5.addMetric(m9);
            d5.addMetric(m10);
            scenario.addDimension(d5);

        } else {
            Dimension d1 = new Dimension("Process Efficiency", 40);
            Metric m1 = new Metric("Defect Density", 70, "Lower", 0, 5, "errors");
            Metric m2 = new Metric("Schedule Variance", 30, "Lower", 0, 20, "%");
            if (mode.equals("Education")) {
                m1.setValue(0.9);
                m2.setValue(4.0);
            } else {
                m1.setValue(1.5);
                m2.setValue(8.0);
            }
            d1.addMetric(m1);
            d1.addMetric(m2);
            scenario.addDimension(d1);

            Dimension d2 = new Dimension("Maintainability", 30);
            Metric m3 = new Metric("Code Review Time", 100, "Lower", 0, 48, "hours");
            m3.setValue(mode.equals("Education") ? 18.0 : 30.0);
            d2.addMetric(m3);
            scenario.addDimension(d2);

            Dimension d3 = new Dimension("Resource Use", 30);
            Metric m4 = new Metric("Budget Overrun", 100, "Lower", 0, 5000, "$");
            m4.setValue(mode.equals("Education") ? 1200.0 : 2200.0);
            d3.addMetric(m4);
            scenario.addDimension(d3);
        }
    }
}