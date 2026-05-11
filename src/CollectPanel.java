import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CollectPanel extends JPanel {
    public CollectPanel(MeasurementController controller, MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Measurement Collection", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Metric", "Raw Value"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton calcBtn = new JButton("Calculate & Analyse");
        calcBtn.setPreferredSize(new java.awt.Dimension(150, 40));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(calcBtn);
        add(southPanel, BorderLayout.SOUTH);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                model.setRowCount(0);
                if (controller.getScenario() != null) {
                    for (Dimension d : controller.getScenario().getDimensions()) {
                        for (Metric m : d.getMetrics()) {
                            model.addRow(new Object[]{m.getName(), m.getValue()});
                        }
                    }
                }
            }
        });

        calcBtn.addActionListener(e -> {
            try {
                int row = 0;
                for (Dimension d : controller.getScenario().getDimensions()) {
                    for (Metric m : d.getMetrics()) {
                        String valueStr = table.getValueAt(row++, 1).toString();
                        m.setValue(Double.parseDouble(valueStr));
                    }
                }
                frame.showPanel("5");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values!");
            }
        });
    }
}