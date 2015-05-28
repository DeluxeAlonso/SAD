/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Random;

/**
 *
 * @author robert
 */
public class Randomizer {
    static final Random randomGenerator = new Random();

    public static boolean doIt(float probability) {
        float randomFloat = randomGenerator.nextFloat();
        return (randomFloat < probability);
    }

    //Numero aleatorio [0,max[
    public static int randomInt(int max) {
        // Returns a randomly generated int
        return randomGenerator.nextInt(max);
    }

    //Numero aleatorio [0.0f,1.0f[
    public static float randomFloat() {
        // Returns a randomly generated int
        return randomGenerator.nextFloat();
    }
}
