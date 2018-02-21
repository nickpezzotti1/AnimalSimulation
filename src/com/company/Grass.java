package com.company;

import java.util.Random;

/**
 * A simple model of grass
 * grass is a plant
 *
 * @author Dario Nunez and Nicholas Pezzoti
 * @version 19/02/2018
 */
public class Grass extends Plant {

    private static final int BREEDING_AGE = 1;
    private static final int MAX_AGE = 7;
    private static final double BREEDING_PROBABILITY = 0.2;
    private static final int MAX_LITTER_SIZE = 6;
    private static final int FOOD_VALUE = 6;
    private static final Random rand = Randomizer.getRandom();
    private static final boolean ACTIVE_AT_NIGHT = true;
    //equivalent to a boolean indicating it cannot get sick
    private static final double IMMUNE_TO_DISEASE = 1;

    /**
     * Creates an instance of Grass assigning it a field, location, random age (if it
     * is spawned as part of the generate method in Simulator) and whether it is sick.
     * @param randomAge whether to spawn it as part of populating the board or as an offspring
     * @param field the field the simulation runs on
     * @param location the location of the grass in the field
     * @param isSick whether the grass is sick or not
     */
    public Grass(boolean randomAge, Field field, Location location, boolean isSick) {
        super(field, location, isSick);
        setAge(0);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }

    /**
     * @return the food value of the rabbit
     */
    protected int getFoodValue() {
        return FOOD_VALUE;
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
     * @return the max age of the rabbit
     */
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * @return the level of immunity to disease
     */
    public double getImmunity() {
        return IMMUNE_TO_DISEASE;
    }

    /**
     * @return whether the rabbit is active at night
     */
    public boolean isActiveAtNight() {
        return ACTIVE_AT_NIGHT;
    }
}
