package GUI;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

public class ChartPanel extends JPanel {

    private List<SimulationResult> results;
    private final Color[] barColors = {
            new Color(224, 77, 77),
            new Color(77, 166, 224),
            new Color(77, 224, 138),
            new Color(245, 167, 66),
            new Color(167, 77, 224),
            new Color(224, 217, 77)
    };

    public void setResults(List<SimulationResult> results) {
        this.results = results;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (results == null || results.isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int maxFaults = 0;
        for (SimulationResult res : results) {
            if (res.getFaults() > maxFaults) {
                maxFaults = res.getFaults();
            }
        }
        if (maxFaults == 0) maxFaults = 1;

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int padding = 40;
        int labelPadding = 25;

        int chartWidth = panelWidth - 2 * padding;
        int chartHeight = panelHeight - padding - labelPadding;

        g2.setColor(Color.BLACK);
        g2.drawLine(padding, padding / 2, padding, chartHeight + padding);
        g2.drawLine(padding, chartHeight + padding, padding + chartWidth, chartHeight + padding);

        g2.drawString("0", padding - 15, chartHeight + padding);
        g2.drawString(String.valueOf(maxFaults), padding - 25, padding / 2 + 5);

        int barWidth = chartWidth / (results.size() * 2);
        int barSpacing = barWidth;

        for (int i = 0; i < results.size(); i++) {
            SimulationResult res = results.get(i);

            int barHeight = (int) (((double) res.getFaults() / maxFaults) * chartHeight);

            int x = padding + (i * (barWidth + barSpacing)) + (barSpacing / 2);
            int y = chartHeight + padding - barHeight;

            g2.setColor(barColors[i % barColors.length]);
            g2.fillRect(x, y, barWidth, barHeight);

            g2.setColor(Color.BLACK);
            FontMetrics fm = g2.getFontMetrics();
            int labelWidth = fm.stringWidth(res.getAlgorithmName());
            g2.drawString(res.getAlgorithmName(), x + (barWidth / 2) - (labelWidth / 2), chartHeight + padding + labelPadding - 5);

            String faultValue = String.valueOf(res.getFaults());
            int valueWidth = fm.stringWidth(faultValue);
            g2.drawString(faultValue, x + (barWidth / 2) - (valueWidth / 2), y - 5);
        }
    }
}
