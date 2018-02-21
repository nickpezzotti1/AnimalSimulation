package com.company;

import java.util.*;
/**
 * Write a description of class Weather here.
 *
 * @author Nicholas Pezzotti
 * @version 1.0.0
 */
public class Weather
{
    private WeatherType currentWeather;
    private final int CHANCE_OF_SUN = 10; 
    private final int CHANCE_OF_RAIN = 88; // Effectively 78
    private final int CHANCE_OF_ACID_RAIN = 2; // Effectively 10

    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        currentWeather = WeatherType.SUNNY;
    }

    /**
     * Gets a new weather type based on a random outcome, as described in the fields.
     * @return the new WeatherType.
     */
    public WeatherType nextWeather() {
        Random rand = Randomizer.getRandom();
        int randInt = rand.nextInt(100);
        if (randInt <= CHANCE_OF_SUN) {
            currentWeather = WeatherType.SUNNY; 
        }
        else if (randInt <= CHANCE_OF_RAIN) {
            currentWeather = WeatherType.RAIN; 
        }
        if (randInt <= CHANCE_OF_ACID_RAIN) {
            currentWeather = WeatherType.ACID_RAIN; 
        }
        return currentWeather;
    }

    /**
     * @return The current WeatherType.
     */
    public WeatherType getCurrentWeather() {
        return currentWeather; 
    }
}
