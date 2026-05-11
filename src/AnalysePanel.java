import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalysePanel extends JPanel {
    private MeasurementController controller;
    private JPanel chartArea;
    private JTextArea scoreArea;
    private JTextArea gapArea;

    public AnalysePanel(MeasurementController controller, MainFrame frame) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Measurement Analysis", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.setPreferredSize(new java.awt.Dimension(220, 0));
        westPanel.setBorder(BorderFactory.createTitledBorder("Dimension Scores"));
        westPanel.setBackground(Color.WHITE);
        scoreArea = new JTextArea();
        scoreArea.setFont(new Font("Arial", Font.BOLD, 14));
        scoreArea.setEditable(false);
        westPanel.add(scoreArea, BorderLayout.CENTER);

        chartArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                renderRadar(g);
            }
        };
        chartArea.setBackground(Color.WHITE);
        chartArea.setBorder(BorderFactory.createTitledBorder("Radar Chart"));

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setPreferredSize(new java.awt.Dimension(0, 140));
        southPanel.setBorder(BorderFactory.createTitledBorder("Gap Analysis"));
        southPanel.setBackground(Color.WHITE);
        gapArea = new JTextArea();
        gapArea.setFont(new Font("Arial", Font.PLAIN, 13));
        gapArea.setEditable(false);
        southPanel.add(gapArea, BorderLayout.CENTER);

        JPanel mainCenter = new JPanel(new BorderLayout());
        mainCenter.add(westPanel, BorderLayout.WEST);
        mainCenter.add(chartArea, BorderLayout.CENTER);
        mainCenter.add(southPanel, BorderLayout.SOUTH);
        add(mainCenter, BorderLayout.CENTER);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                updateAnalysisData();
                chartArea.repaint();
            }
        });
    }

    private void updateAnalysisData() {
        if (controller.getScenario() == null) return;

        StringBuilder scoreBuilder = new StringBuilder();
        double minScore = 6.0;
        String weakestDimension = "";

        for (Dimension d : controller.getScenario().getDimensions()) {
            double score = d.calculateScore();
            scoreBuilder.append(d.getName()).append(" Score: ")
                    .append(String.format("%.2f", score)).append("\n\n");

            if (score < minScore) {
                minScore = score;
                weakestDimension = d.getName();
            }
        }
        scoreArea.setText(scoreBuilder.toString());

        double gapValue = 5.0 - minScore;
        String qualityLevel = minScore >= 4.0 ? "Excellent" : (minScore >= 3.0 ? "Good" : "Needs Improvement");

        String gapText = "Lowest Dimension: " + weakestDimension + "\n" +
                "Score: " + String.format("%.2f", minScore) + "\n" +
                "Gap Value: " + String.format("%.2f", gapValue) + "\n" +
                "Quality Level: " + qualityLevel + "\n\n" +
                "This dimension has the lowest score and requires the most improvement.";
        gapArea.setText(gapText);
    }

    private void renderRadar(Graphics g) {
        if (controller.getScenario() == null) return;
        List<Dimension> dims = controller.getScenario().getDimensions();
        int count = dims.size();
        if (count < 2) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = chartArea.getWidth() / 2;
        int cy = chartArea.getHeight() / 2;
        int maxR = Math.min(chartArea.getWidth(), chartArea.getHeight()) / 3;

        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 1; i <= 5; i++) {
            int r = maxR * i / 5;
            g2.drawOval(cx - r, cy - r, 2 * r, 2 * r);
        }

        int[] xPts = new int[count];
        int[] yPts = new int[count];

        for (int i = 0; i < count; i++) {
            double angle = 2 * Math.PI * i / count - Math.PI / 2;
            double score = dims.get(i).calculateScore();

            int xEnd = cx + (int) (maxR * Math.cos(angle));
            int yEnd = cy + (int) (maxR * Math.sin(angle));
            g2.setColor(Color.DARK_GRAY);
            g2.drawLine(cx, cy, xEnd, yEnd);

            g2.drawString(dims.get(i).getName(), xEnd - 20, yEnd > cy ? yEnd + 15 : yEnd - 5);

            xPts[i] = cx + (int) ((maxR * score / 5.0) * Math.cos(angle));
            yPts[i] = cy + (int) ((maxR * score / 5.0) * Math.sin(angle));
        }

        g2.setColor(new Color(0, 120, 255, 40));
        g2.fillPolygon(xPts, yPts, count);
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2));
        g2.drawPolygon(xPts, yPts, count);
    }
}