package com.company;

import java.util.Random;

/**
 * A simple model of a fox
 * fox is a land animal
 *
 * @author Nicholas Pezzoti
 * @version 19/02/2018
 */
public class Fox extends GroundAnimal {
    private static final int BREEDING_AGE = 8;
    private static final int MAX_AGE = 37;
    private static final double BREEDING_PROBABILITY = 0.350;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int FOOD_VALUE = 30;
    private static final Random rand = Randomizer.getRandom();
    private static final int MAX_FOOD = 17;
    private static final boolean ACTIVE_AT_NIGHT = true;
    private static final Class[] possiblePrey = new Class[]{Rabbit.class} ;
    private static final double IMMUNE_TO_DISEASE = 0.02;

    /**
     * Creates an instance of Fox assigning it a field, location, random age (if it
     * is spawned as part of the generate method in Simulator) and whether it is sick.
     * All offspring are spawned with a food value of half their maximum food value
     * @param randomAge whether to spawn it as part of populating the board or as an offspring
     * @param field the field the simulation runs on
     * @param location the location of the fox in the field
     * @param isSick whether the fox is sick or not
     */
    public Fox(boolean randomAge, Field field, Location location, boolean isSick) {
        super(rand.nextBoolean(), field, location, isSick);

        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(MAX_FOOD));
        }
        else {
            setAge(0);
            setFoodLevel(MAX_FOOD/2);
        }
    }

    /**
     * @return the food value of the fox
     */
    protected int getFoodValue() {
        return FOOD_VALUE;
    }

    /**
     * Increments the fox's food value by the food value of the prey at the
     * moment of death
     * @param preyFoodValue the prey's food value
     */
    protected void feed(int preyFoodValue) {
        setFoodLevel(getFoodLevel() + preyFoodValue);
    }

    /**
     * @return the max age of the fox
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * @return the breeding age of the fox
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return the breeding probability of the fox
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return the max litter size of the fox
     */
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return the maximum food a fox can eat
     */
    public int getMaxFood() {
        return MAX_FOOD;
    }

    /**
     * @return whether the fox is active at night
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