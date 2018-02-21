package com.company;

import java.util.Random;

/**
 * A simple model of a rabbit
 * rabbit is a land animal
 *
 * @author Dario Nunez and Nicholas Pezzoti
 * @version 19/02/2018
 */
public class Rabbit extends GroundAnimal {
    private static final int BREEDING_AGE = 5;
    private static final int MAX_AGE = 25;
    private static final double BREEDING_PROBABILITY = 0.87;
    private static final int MAX_LITTER_SIZE = 6;
    private static final int MAX_FOOD = 7;
    private static final int FOOD_VALUE = 19;
    private static final Random rand = Randomizer.getRandom();
    private static final boolean ACTIVE_AT_NIGHT = true;
    private static final double IMMUNE_TO_DISEASE = 0.02;
    private static final Class[] possiblePrey = new Class[]{Grass.class} ;

    /**
     * Creates an instance of Rabbit assigning it a field, location, random age (if it
     * is spawned as part of the generate method in Simulator) and whether it is sick.
     * All offspring are spawned with a food value of half their maximum food value
     * @param randomAge whether to spawn it as part of populating the board or as an offspring
     * @param field the field the simulation runs on
     * @param location the location of the rabbit in the field
     * @param isSick whether the rabbit is sick or not
     */
    public Rabbit(boolean randomAge, Field field, Location location, boolean isSick) {
        super(rand.nextBoolean(), field, location, isSick);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(MAX_FOOD));
        } else {
            setAge(0);
            setFoodLevel(MAX_FOOD/2);
        }
    }

    /**
     * @return the food value of the rabbit
     */
    protected int getFoodValue() {
        return FOOD_VALUE;
    }

    /**
     * Increments the rabbit's food value by the food value of the prey at the
     * moment of death
     * @param preyFoodValue the prey's food value
     */
    protected void feed(int preyFoodValue) {
        setFoodLevel(getFoodLevel() + preyFoodValue);
    }

    /**
     * @return the max age of the rabbit
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * @return the breeding age of the rabbit
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return the breeding probability of the rabbit
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return the max litter size of the rabbit
     */
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return the maximum food a rabbit can eat
     */
    public int getMaxFood() {
        return MAX_FOOD;
    }

    /**
     * @return whether the rabbit is active at night
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