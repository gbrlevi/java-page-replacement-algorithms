import GUI.SimulatorGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimulatorGUI().setVisible(true);
            }
        });
    }
}

