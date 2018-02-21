package com.company;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics and behaviours of plants.
 * Implements spreading and a different act method from animals
 *
 * @author Dario Nunez and Nicholas Pezzotti
 * @version 19/02/2018
 */
public abstract class Plant extends Organism{
    private static final Random rand = Randomizer.getRandom();
    private static final double SICK_MUTATION_PROBABILITY = 0.02;

    /**
     * Creates a Plant at a location
     * @param field the field given to the plant
     * @param location the location of the plant in the field
     * @param isSick whether the plant is spawned sick or not
     */
    Plant(Field field, Location location, boolean isSick) {
        super(field, location, isSick);
    }

    /**
     * Makes the plants age, spread and die, the latter one is affected
     * by the weather
     * @param newPlants list of plant offspring
     */
    public void act(List<Organism> newPlants) {
        incrementAge();
        if (isAlive() && getField().isRaining()) {
            spread(newPlants);
        }
    }

    /**
     * Implements the plant's procreation technique. Calculates how many offspring
     * need to be spawned, checks for the correct territory to spawn them on,
     * spawns them around the parent and decides whether they should be sick or not
     * @param newPlants a list of offspring
     */
    private void spread(List<Organism> newPlants) {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            if (isCorrectTerritory(loc)) {
                Plant young;
                if (rand.nextDouble() < SICK_MUTATION_PROBABILITY) {
                    young = (Plant) this.createBaby(false, field, loc, true);
                }
                else {
                    young = (Plant) this.createBaby(false, field, loc, false);
                }
                newPlants.add(young);
            }
        }
    }

    /**
     * Checks if the location a plant is being placed at is allowed
     * @param nextLocation The location the organism is trying to move to
     * @return whether plants are allowed to be placed at that location
     */
    public boolean isCorrectTerritory(Location nextLocation) {
        return !getField().isWater(nextLocation);
    }

    /**
     * @return the number of offspring produced taking into account breeding probability
     * and max litter size
     */
    private int breed() {
        int births = 0;
        if(canSpread() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * @return whether a plant is old enough to spread
     */
    private boolean canSpread()
    {
        return getAge() >= getBreedingAge();
    }
}
