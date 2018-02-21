package com.company;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.HashMap; 

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits, foxes, crocodiles, fish and grass
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.09;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.20;
    private static final double CROCODILE_CREATION_PROBABILITY = 0.036;
    private static final double GRASS_CREATION_PROBABILITY = 0.70;
    private static final double FISH_CREATION_PROBABILITY = 0.2;
    private static final HashMap<Class, Double> creationProabilities = new HashMap<Class, Double>();

    // List of animals in the field.
    private List<Organism> organisms;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    private int depth, width;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        organisms = new ArrayList<>();
        this.width = width;
        this.depth = depth;
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, field );
        view.setColor(Rabbit.class, Color.MAGENTA);
        view.setColor(Fox.class, Color.GRAY);
        view.setColor(Crocodile.class, Color.RED);
        view.setColor(Grass.class, Color.GREEN.darker());
        view.setColor(Fish.class, Color.ORANGE);
        
        creationProabilities.put(Rabbit.class, RABBIT_CREATION_PROBABILITY);
        creationProabilities.put(Fox.class, FOX_CREATION_PROBABILITY);
        creationProabilities.put(Crocodile.class, CROCODILE_CREATION_PROBABILITY);
        creationProabilities.put(Grass.class, GRASS_CREATION_PROBABILITY);
        creationProabilities.put(Fish.class, FISH_CREATION_PROBABILITY);
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    private void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);// uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * organism.
     */
    private void simulateOneStep()
    {
        step++;
        
        field.incrementTime(); 

        // Provide space for newborn animals.
        List<Organism> newOrganisms = new ArrayList<>();
        // Let all organisms act.
        for(Iterator<Organism> it = organisms.iterator(); it.hasNext(); ) {
            Organism organism = it.next();
            organism.nextStep(newOrganisms);
            if(! organism.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        organisms.addAll(newOrganisms);

        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    private void reset()
    {
        step = 0;
        organisms.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Spawns an anonymous object of type c (class of type Organism that we want to instantiate) at a given row and
     * column of the board.
     * @param inWater Specifies if the location has to be water
     * @param c The data-type of the Organism to instantiate
     * @param row The row in which we want to spawn the organism
     * @param col The col in which we want to spawn the organism
     */
    private void spawnAnimal(boolean inWater, Class c, int row, int col, boolean isSick) {
        try {
            Organism organism = (Organism) c.getConstructor(boolean.class, Field.class, Location.class, boolean.class).newInstance(true, field, new Location(row, col), false);//requires bool
            organisms.add(organism);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Randomly populate the field with all types of organisms
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(field.isWater(row, col)) {
                    for (Class c : creationProabilities.keySet()) {
                        if (WaterAnimal.class.isAssignableFrom(c) || Amphibian.class.isAssignableFrom(c)) {
                            if (rand.nextDouble() <= creationProabilities.get(c)) {
                                spawnAnimal(true, c, row, col, false);
                            }
                        }
                    }
                }
                else { //if it's land
                    for (Class c : creationProabilities.keySet()) {
                        if (GroundAnimal.class.isAssignableFrom(c) || Amphibian.class.isAssignableFrom(c) || Plant.class.isAssignableFrom(c)) {
                            if (rand.nextDouble() <= creationProabilities.get(c)) {
                                spawnAnimal(false, c, row, col, false);
                            }
                        }
                    }
                }
                // else leave the location empty.
            }
        }
    }
    

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
