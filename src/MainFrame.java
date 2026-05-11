import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel container = new JPanel(cardLayout);
    private JPanel indicatorPanel;
    private JLabel[] stepLabels;
    private final String[] stepNames = {"Profile", "Define", "Plan", "Collect", "Analyse"};

    public MainFrame(MeasurementController controller) {
        setTitle("ISO 15939 Software Metric Analyzer");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createStepIndicator();

        container.add(new ProfilePanel(controller, this), "1");
        container.add(new DefinePanel(controller, this), "2");
        container.add(new PlanPanel(controller, this), "3");
        container.add(new CollectPanel(controller, this), "4");
        container.add(new AnalysePanel(controller, this), "5");

        add(indicatorPanel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);

        updateStepIndicator(1);
        setVisible(true);
    }

    private void createStepIndicator() {
        indicatorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        indicatorPanel.setBackground(new Color(250, 250, 250));
        indicatorPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)));

        stepLabels = new JLabel[stepNames.length];
        for (int i = 0; i < stepNames.length; i++) {
            stepLabels[i] = new JLabel(stepNames[i]);
            stepLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            stepLabels[i].setForeground(Color.GRAY);
            indicatorPanel.add(stepLabels[i]);

            if (i < stepNames.length - 1) {
                JLabel arrow = new JLabel(" ➔ ");
                arrow.setForeground(Color.LIGHT_GRAY);
                indicatorPanel.add(arrow);
            }
        }
    }

    public void updateStepIndicator(int currentStep) {
        for (int i = 0; i < stepNames.length; i++) {
            int stepNum = i + 1;
            if (stepNum < currentStep) {
                stepLabels[i].setText("✔  " + stepNames[i]);
                stepLabels[i].setForeground(new Color(46, 125, 50));
                stepLabels[i].setFont(new Font("Segoe UI", Font.BOLD, 14));
            } else if (stepNum == currentStep) {
                stepLabels[i].setText("[ " + stepNames[i] + " ]");
                stepLabels[i].setForeground(new Color(103, 58, 183));
                stepLabels[i].setFont(new Font("Segoe UI", Font.BOLD, 14));
            } else {
                stepLabels[i].setText(stepNames[i]);
                stepLabels[i].setForeground(Color.LIGHT_GRAY);
                stepLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }
        }
        indicatorPanel.repaint();
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
        updateStepIndicator(Integer.parseInt(name));
    }
}