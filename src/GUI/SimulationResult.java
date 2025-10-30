package GUI;

public class SimulationResult {

    private final String algorithmName;
    private final int faults;

    public SimulationResult(String algorithmName, int faults) {
        this.algorithmName = algorithmName;
        this.faults = faults;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getFaults() {
        return faults;
    }
}
