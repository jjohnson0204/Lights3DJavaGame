package com.lightsengine.core.utils;

import org.joml.Random;
import java.security.SecureRandom;

public class RandomNumberGenerator {
    private static final SecureRandom generator = new SecureRandom();
    public static int NumberBetween(int minimumValue, int maximumValue)
    {
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] randomNumber = new byte[1];
        byte[] randomNumber = new byte[1];
        generator.nextBytes(randomNumber);
        double asciiValueOfRandomCharacter = randomNumber[0];
        // We are using Math.Max, and subtracting 0.00000000001,
        // to ensure "multiplier" will always be between 0.0 and .99999999999
        // Otherwise, it's possible for it to be "1", which causes problems in our rounding.
        double multiplier = Math.max(0, (asciiValueOfRandomCharacter / 255d) - 0.00000000001d);
        // We need to add one to the range, to allow for the rounding done with Math.Floor
        int range = maximumValue - minimumValue + 1;
        double randomValueInRange = Math.floor(multiplier * range);
        return (int)(minimumValue + randomValueInRange);
    }
    // Simple version, with less randomness.
    //
    // If you want to use this version,
    // you can delete (or comment out) the NumberBetween function above,
    // and rename this from SimpleNumberBetween to NumberBetween
    private static final Random _simpleGenerator = new Random();
    public static int SimpleNumberBetween(int minimumValue, int maximumValue)
    {
//C# TO JAVA CONVERTER TASK: There is no two-argument version of 'nextInt' in Java:
        return _simpleGenerator.nextInt(minimumValue) + maximumValue + 1;
    }

}
