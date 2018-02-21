package com.company;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Provides the basis for the animal hierarchy. Contains common attributes
 * and behaviours shared by all organisms in the simulation, defines
 * abstract methods that are implemented in subclasses.
 *
 * @author Dario Nunez and Nicholas Pezzotti
 * @version 19/02/2018
 */
public abstract class Organism {
    private boolean alive;
    private int age;
    private Field field;
    private Location location;
    private boolean isSick;

    /**
     * Creates an organism which has a field, a location
     * and a chance of being sick
     * @param field the field the organism is in
     * @param location the specific location within the field
     * @param isSick whether the organism is sick or not
     */
    protected Organism(Field field, Location location, boolean isSick) {
        setAlive(true); //$ alive = true;
        this.setField(field);
        setLocation(location);
        this.isSick = isSick; 
    }

    /**
     * @return Whether the organism is active at night or not
     */
    abstract boolean isActiveAtNight();

    /**
     * Checks if a location is correct territory for the specific organism
     * this applies to animals that live exclusively on water or land
     * @param nextLocation The location the organism is trying to move to
     * @return true if the location is appropriate, false otherwise
     */
    abstract boolean isCorrectTerritory(Location nextLocation);

    /**
     * @return the chance of the organism being immune to disease
     */
    abstract double getImmunity();

    /**
     * Key method that drives the simulation by allowing every organism
     * to act on their specific behaviours and interact with one another
     * @param newOrganisms a list of offspring as a result of breeding
     */
    abstract void act(List<Organism> newOrganisms);

    /**
     * @return the maximum age of the organism
     */
    abstract int getMaxAge();

    /**
     * @return the minimum age organisms have to be to breed
     */
    abstract int getBreedingAge();

    /**
     * @return the probability the organism will breed upon
     * encountering a suitable mate
     */
    abstract double getBreedingProbability();

    /**
     * @return the maximum number of offspring an organism can produce
     * when procreating
     */
    abstract int getMaxLitterSize();

    /**
     * @return the food value held by an organism. It is used to regulate
     * their hunger and also to increment predator's food value when eaten
     */
    abstract int getFoodValue();

    /**
     * Triggers the act method on organisms only if is day time or if the
     * organism is active at night (makes night behaviour possible)
     * @param newOrganisms list of offspring
     */
    public void nextStep(List<Organism> newOrganisms) {
        //Because the organisms list is updated after the iterator is finished
        if (field != null) {
            if (field.getTimeOfDay() || isActiveAtNight()){
                act(newOrganisms);  
            }
        }
    }

    /**
     * Increments the age of the organism by 1 unless events that affect them
     * take place, like acid rain or illness, then they age faster.
     */
    protected void incrementAge() {
        int increment = (field.isAcidRaining()? 2:1) + (isSick()? 1:0);
        setAge(getAge()+increment); //$ age += 1; if (age > getMaxAge()) {setDead()}
        if (getAge() > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Kills the organism and clears its trace in field
     */
    protected void setDead() {
        this.alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * @return Whether the animal is alive or not
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Gives a state to an organism (alive or dead)
     * @param alive whether its alive, true, or dead, false
     */
    protected void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * @return the current age of the organism
     */
    protected int getAge() {
        return age;
    }

    /**
     * sets the age of the organism to a value
     * @param age the age value to be set
     */
    protected void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the field given to the organism upon construction
     */
    protected Field getField() {
        return field;
    }

    /**
     * Gives organism a field to be simulated on
     * @param field the field object
     */
    protected void setField(Field field) {
        this.field = field;
    }

    /**
     * @return the location object held by every organism
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Gives a new location to an organism
     * @param newlocation the new location object to be stored in the organism
     */
    protected void setLocation(Location newlocation) {
        if (location != null) {
            field.clear(location);
        }
        this.location = newlocation;
        field.place(this, newlocation);
    }

    /**
     * @return whether an organism is sick or not
     */
    protected boolean isSick() {
        return isSick;
    }

    /**
     * Creates an offspring of any type of organism by invoking the correct constructor
     * depending on which object the method is being called on.
     * @param randomAge the age the offspring will spawn with
     * @param field the field that will be added to the offspring object
     * @param location the location it will be placed in
     * @param isSick whether the offspring will be spawned sick or not
     * @return the offspring object. The dynamic type will be of the subtype the method was
     * called on
     */
    protected Organism createBaby(boolean randomAge, Field field, Location location, boolean isSick) {
        Organism young = null;
        try {
            young = getClass().getConstructor(boolean.class, Field.class, Location.class, boolean.class).newInstance(randomAge, field, location, isSick);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return young;
    }
}
