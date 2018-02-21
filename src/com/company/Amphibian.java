package com.company;

/**
 * A class necessary for the implementation of amphibian animals
 * Allows them to exist in both water an land
 *
 * @author Nicholas Pezzotti
 * @version 19/02/2018
 */
public abstract class Amphibian extends Animal
{
    /**
     * Creates a new amphibian at a location in a field
     * @param gender the gender given to the animal
     * @param field the field where it exists
     * @param location its specific location in the field
     * @param isSick whether it's sick or not
     */
    public Amphibian(boolean gender, Field field, Location location, boolean isSick)
    {
        super(gender, field, location, isSick);
    }

    /**
     * Checks if the terrain the organism wants to move to is appropriate for it
     * @param nextLocation The location the organism is trying to move to
     * @return true is the organism is allowed in th new terrain, false otherwise
     */
    public boolean isCorrectTerritory(Location nextLocation) {
        return true; 
    }
}
