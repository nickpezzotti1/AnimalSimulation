package com.company;

import java.util.Random;

/**
 * A simple model of a fish
 * fish is a water animal
 *
 * @author Nicholas Pezzoti
 * @version 19/02/2018
 */
public class Fish extends WaterAnimal {
    private static final int BREEDING_AGE = 3;
    private static final int MAX_AGE = 10;
    private static final double BREEDING_PROBABILITY = 0.2;
    private static final int MAX_LITTER_SIZE = 10;
    private static final int MAX_FOOD = 1;
    private static final int FOOD_VALUE = 1;
    private static final Random rand = Randomizer.getRandom();
    private static final boolean ACTIVE_AT_NIGHT = true;
    private static final double IMMUNE_TO_DISEASE = 0.10;
    private static final Class[] possiblePrey = new Class[]{} ;

    /**
     * Creates an instance of fish assigning it a field, location, random age (if it
     * is spawned as part of the generate method in Simulator) and whether it is sick.
     * The food level is set to the maximum allowed value as fish eat plankton which
     * is not modeled in this simulation. It is assumed to be an endless and constant
     * source of food for the fish
     * @param randomAge whether to spawn it as part of populating the board or as an offspring
     * @param field the field the simulation runs on
     * @param location the location of the fish in the field
     * @param isSick whether the fish is sick or not
     */
    public Fish(boolean randomAge, Field field, Location location, boolean isSick) {
        super(rand.nextBoolean(), field, location, isSick);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(Integer.MAX_VALUE);
        } else {
            setAge(0);
            setFoodLevel(Integer.MAX_VALUE);
        }
    }

    /**
     * @return the food value of the fish
     */
    protected int getFoodValue() {
        return FOOD_VALUE;
    }

    /**
     * Increments the fish's food value by the food value of the prey at the
     * moment of death
     * @param preyFoodValue the prey's food value
     */
    protected void feed(int preyFoodValue) {
        setFoodLevel(getFoodLevel() + preyFoodValue);
    }

    /**
     * @return the max age of the fish
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * @return the breeding age of the fish
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return the breeding probability of the fish
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return the max litter size of the fish
     */
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return the maximum food a fish can eat
     */
    public int getMaxFood() {
        return MAX_FOOD;
    }

    /**
     * overrides the the incrementHunger method so fish don't die of hunger
     * it is assumed plankton is their constant supply of food
     */
    @Override 
    public void incrementHunger() {}

    /**
     * @return whether the fish is active at night
     */
    public boolean isActiveAtNight() {
        return ACTIVE_AT_NIGHT;
    }

    /**
     * @return the level of immunity to disease
     */
    public double getImmunity() {
        return IMMUNE_TO_DISEASE;
    }

    /**
     * @return a list of classes (organisms) that crocodiles can eat
     */
    public Class[] getPossiblePrey() {return possiblePrey;}
}