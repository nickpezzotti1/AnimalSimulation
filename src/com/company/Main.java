package com.company;

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
