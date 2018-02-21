package com.company;

/**
 * Main class used for initial setup of the Simulator
 * Used for testing outside of BlueJ
 *
 * @author Dario Nunez and Nicholas Pezzotti
 * @version 1
 */
public class Main {

    /**
     * Provides the window size for Simulator View
     * and runs a long simulation
     * @param args arguments
     */
    public static void main(String[] args) {
        Simulator sim = new Simulator(100,200);
        sim.runLongSimulation();
    }
}
