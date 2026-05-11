import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanPanel extends JPanel {
    public PlanPanel(MeasurementController controller, MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Measurement Planning", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Dimension", "Metric", "Coefficient", "Direction", "Range", "Unit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton nextBtn = new JButton("Go to Collection");
        nextBtn.setPreferredSize(new java.awt.Dimension(150, 40));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(nextBtn);
        add(southPanel, BorderLayout.SOUTH);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                model.setRowCount(0);
                if (controller.getScenario() != null) {
                    for (Dimension d : controller.getScenario().getDimensions()) {
                        for (Metric m : d.getMetrics()) {
                            model.addRow(new Object[]{
                                    d.getName(),
                                    m.getName(),
                                    m.getCoefficient(),
                                    m.getDirection() + "_IS_BETTER",
                                    m.getMin() + " - " + m.getMax(),
                                    m.getUnit()
                            });
                        }
                    }
                }
            }
        });

        nextBtn.addActionListener(e -> frame.showPanel("4"));
    }
}