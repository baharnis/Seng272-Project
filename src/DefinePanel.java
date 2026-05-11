import javax.swing.*;
import java.awt.*;

public class DefinePanel extends JPanel {
    private JComboBox<String> scenarioBox;
    private JRadioButton productBtn;
    private JRadioButton processBtn;
    private JRadioButton eduBtn;
    private JRadioButton healthBtn;

    public DefinePanel(MeasurementController controller, MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        centerPanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Define Measurement Scope", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        centerPanel.add(titleLabel);

        JPanel qPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        qPanel.setBorder(BorderFactory.createTitledBorder("Quality Type"));
        productBtn = new JRadioButton("Product", true);
        processBtn = new JRadioButton("Process");
        ButtonGroup qGroup = new ButtonGroup();
        qGroup.add(productBtn);
        qGroup.add(processBtn);
        qPanel.add(productBtn);
        qPanel.add(processBtn);
        centerPanel.add(qPanel);

        JPanel mPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mPanel.setBorder(BorderFactory.createTitledBorder("Scope Mode"));
        eduBtn = new JRadioButton("Education", true);
        healthBtn = new JRadioButton("Health");
        ButtonGroup mGroup = new ButtonGroup();
        mGroup.add(eduBtn);
        mGroup.add(healthBtn);
        mPanel.add(eduBtn);
        mPanel.add(healthBtn);
        centerPanel.add(mPanel);

        JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sPanel.setBorder(BorderFactory.createTitledBorder("Target Scenario"));
        scenarioBox = new JComboBox<>();
        updateScenarios();
        sPanel.add(scenarioBox);
        centerPanel.add(sPanel);

        eduBtn.addActionListener(e -> updateScenarios());
        healthBtn.addActionListener(e -> updateScenarios());

        add(centerPanel, BorderLayout.CENTER);

        JButton nextBtn = new JButton("Next Step");
        nextBtn.setPreferredSize(new java.awt.Dimension(120, 40));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(nextBtn);
        add(southPanel, BorderLayout.SOUTH);

        nextBtn.addActionListener(e -> {
            String type = productBtn.isSelected() ? "Product Quality" : "Process Quality";
            String mode = eduBtn.isSelected() ? "Education" : "Health";
            String selectedScenario = (String) scenarioBox.getSelectedItem();

            controller.initializeScenario(mode, type, selectedScenario);
            frame.showPanel("3");
        });
    }

    private void updateScenarios() {
        scenarioBox.removeAllItems();
        if (eduBtn.isSelected()) {
            scenarioBox.addItem("Scenario X - Campus Hub");
            scenarioBox.addItem("Scenario Y - Scholar Net");
        } else {
            scenarioBox.addItem("Scenario Alpha - Med Care");
            scenarioBox.addItem("Scenario Beta - Cardio Core");
        }
    }
}