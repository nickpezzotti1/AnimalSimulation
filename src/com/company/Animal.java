package com.company;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics and behaviours of animals.
 * Implements moving, breeding and feeding behaviours particular to
 * the animal branch.
 *
 * @author Nicholas Pezzotti
 * @version 19/02/2018
 */
public abstract class Animal extends Organism {
    private boolean gender;
    private int foodLevel;
    private static final Random rand = Randomizer.getRandom();
    private static final double SICK_MUTATION_PROBABILITY = 0.1;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean gender, Field field, Location location, boolean isSick)
    {
        super(field, location, isSick);
        setGender(gender);
    }

    /**
     * Increments the animal's food value by the prey's food value
     * @param preyFoodValue the prey's food value
     */
    abstract void feed(int preyFoodValue);

    /**
     * @return the maximum value hte food level of the animal can get to.
     * The maximum food the animal can consume
     */
    abstract int getMaxFood();

    /**
     * @return an array of type class with the classes (organisms) that
     * the animal considers prey (organisms it can eat)
     */
    abstract Class[] getPossiblePrey();

    /**
     * Implements the act method in Organism. Specifies what animals do.
     * Makes them age, be hungry, breed, feed, move and die.
     * @param newOrganisms a list of offspring as a result of breeding
     */
    public void act(List<Organism> newOrganisms) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            breed(newOrganisms);
            Location newLocation = findFood();
            move(newLocation); 
        }
    }

    /**
     * Makes an animal find a free location to move to, if it doesn't
     * it dies because of overcrowding
     * @param nextLocation the animal's next location
     */
    private void move(Location nextLocation) {
        if (nextLocation == null) {
                nextLocation = getField().freeAdjacentLocation(getLocation());
                if (!isCorrectTerritory(nextLocation)) {
                    nextLocation = getField().freeAdjacentLocation(getLocation()); // "overcrowding"
                } 
            }

        if(nextLocation != null) {
            //actually sets the location found above
            setLocation(nextLocation);
        }
        else {
            // Overcrowding.
            setDead();
        }
    }

    /**
     * Decides if the animal can and wants to eat the organism encountered.
     * it checks for class compatibility, state of the prey and whether the
     * animal is full or not
     * @param organism the organism encountered
     * @return whether the animal eats the organism encountered
     */
    private boolean canEat(Organism organism) {
        if (isPrey(organism) && organism.isAlive() && getFoodLevel() < getMaxFood()) {
            return true;
        }
        return false;
    }

    /**
     * Calculates using the breeding probability and the max litter size if the
     * animals breed and how many offspring are created
     * @return the number of offspring
     */
    private int numberOfBirths() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * @return if the animal is old enough to breed
     */
    private boolean canBreed() {
        return getAge() >= getBreedingAge();
    }

    /**
     * Decrements the food level of the animal (makes it hungry) and kills it if
     * it reaches 0 (starves to death)
     */
    protected void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Identifies which animals are in the surrounding locations, decides
     * if breeding is possible and creates new animals with properties
     * based on the attribute values held in the parents
     * @param newOrganisms a list of offspring
     */
    private void breed(List<Organism> newOrganisms) {
        List<Location> surrounding = getField().adjacentLocations(getLocation());
        for (Location location : surrounding) {
            if (getField().getObjectAt(location) != null) {
                Organism partner = (Organism) getField().getObjectAt(location);
                if (partner.getClass() == this.getClass()) {
                    if ((((Animal) partner).getGender() != getGender())) {
                        if ((bothParentsSick(partner) && rand.nextDouble() >= getImmunity()) || (rand.nextDouble() < SICK_MUTATION_PROBABILITY)) {
                            giveBirth(newOrganisms, true);
                        }
                        giveBirth(newOrganisms, false);
                    }
                }
            }
        }
    }

    /**
     * Implements the disease logic where it spreads to the offspring only
     * if both parents have the disease
     * @param partner the animal the object is trying to breed with
     * @return whether both parents are sick or not
     */
    private boolean bothParentsSick(Organism partner) {
        return partner.isSick() && isSick();
    }

    /**
     * Creates offspring and places them around the parent
     * @param offSpring the list of offspring to be spawned
     * @param isSick whether the offspring will spawn sick or not
     */
    private void giveBirth(List<Organism> offSpring, boolean isSick) {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = numberOfBirths();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            if (isCorrectTerritory(loc)) {
                offSpring.add(createBaby(false, field, loc, isSick));
            }
        }
    }

    /**
     * Looks around the object for possible prey, if it's possible to eat an
     * animal, it does so and the method returns the prey's previous location
     * else, null is returned
     * @return the location of the prey if hunt is succesfull or null if it is not
     */
    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Organism organism = (Organism) field.getObjectAt(where);
            if(canEat(organism)) {
                organism.setDead();
                feed(organism.getFoodValue());
                return where;
            }
        }
        return null;
    }

    /**
     * @param organism the organism trying to be eaten
     * @return whether the organism encountered is an edible organism or not
     */
    private boolean isPrey(Organism organism) {
        if (organism == null) return false;
        for (Class c : getPossiblePrey()) {
            if (organism.getClass() ==  c) return true;
        }
        return false;
    }

    /**
     * @return the gender of the animal
     */
    private boolean getGender() {
        return gender;
    }

    /**
     * sets the gender of an animal
     * @param gender either true or false for male and female
     */
    private void setGender(boolean gender) {
        this.gender = gender;
    }

    /**
     * @return the food level of the animal
     */
    public int getFoodLevel() {return foodLevel;}

    /**
     * sets the food level of an animal
     * @param foodLevel the new animal's food level
     */
    public void setFoodLevel(int foodLevel) {this.foodLevel = foodLevel; }
}