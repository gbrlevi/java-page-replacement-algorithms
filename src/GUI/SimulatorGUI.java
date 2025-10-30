package GUI;

import Algorithms.Clock;
import Algorithms.Fifo;
import Algorithms.Lru;
import Algorithms.Optimal;
import Util.IPageReplacementAlgorithm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulatorGUI extends JFrame {

    private JTextField pageStringField;
    private JSpinner framesSpinner;
    private JTextArea resultsTextArea;
    private ChartPanel chartPanel;

    private final List<IPageReplacementAlgorithm> algoritmos;

    public SimulatorGUI() {
        setTitle("Simulador de Algoritmos de Substituição de Páginas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        algoritmos = new ArrayList<>();
        algoritmos.add(new Fifo());
        algoritmos.add(new Lru());
        algoritmos.add(new Optimal());
        algoritmos.add(new Clock());

        initComponents();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        inputPanel.add(new JLabel("Sequência de Páginas (ex: 1,2,3,1):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        pageStringField = new JTextField("7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1", 40);
        inputPanel.add(pageStringField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        inputPanel.add(new JLabel("Número de Quadros:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.9;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        SpinnerModel spinnerModel = new SpinnerNumberModel(3, 1, 32, 1);
        framesSpinner = new JSpinner(spinnerModel);
        inputPanel.add(framesSpinner, gbc);

        add(inputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        resultsTextArea = new JTextArea(8, 40);
        resultsTextArea.setEditable(false);
        resultsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultsTextArea.setBorder(BorderFactory.createTitledBorder("Resultados (Texto)"));
        centerPanel.add(new JScrollPane(resultsTextArea), BorderLayout.NORTH);

        chartPanel = new ChartPanel();
        chartPanel.setBorder(BorderFactory.createTitledBorder("Comparativo Gráfico (Page Faults)"));
        centerPanel.add(chartPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton simulateButton = new JButton("Simular");
        simulateButton.setFont(new Font("Arial", Font.BOLD, 16));
        actionPanel.add(simulateButton);
        add(actionPanel, BorderLayout.SOUTH);

        simulateButton.addActionListener(e -> runSimulation());
    }

    private void runSimulation() {
        String pageString = pageStringField.getText();
        int numFrames = (Integer) framesSpinner.getValue();

        int[] pages;
        try {
            String[] pageStrings = pageString.trim().split("[,\\s]+");
            if (pageStrings[0].isEmpty()) {
                throw new NumberFormatException("String vazia.");
            }
            pages = new int[pageStrings.length];
            for (int i = 0; i < pageStrings.length; i++) {
                pages[i] = Integer.parseInt(pageStrings[i]);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro: Sequência de páginas inválida.\nUse números separados por vírgula ou espaço (ex: 1, 2, 3).",
                    "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder resultsText = new StringBuilder();
        resultsText.append("Simulação com ").append(numFrames).append(" quadros:\n");
        resultsText.append("Sequência: ").append(pageString).append("\n");
        resultsText.append("----------------------------------------\n");

        List<SimulationResult> chartData = new ArrayList<>();

        for (IPageReplacementAlgorithm algo : algoritmos) {
            int faults = algo.simulate(pages, numFrames);

            resultsText.append(String.format("- Método %-8s - %d faltas de página%n", algo.getName(), faults));

            chartData.add(new SimulationResult(algo.getName(), faults));
        }

        resultsTextArea.setText(resultsText.toString());
        chartPanel.setResults(chartData);
    }
}
