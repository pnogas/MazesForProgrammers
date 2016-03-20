package com.paulnogas.mazesforprogrammers;

import java.util.Random;
import java.util.Set;

/**
 * Created by Paul Nogas on 2016-03-19.
 */
public class Utils {
    private static Random random = new Random();

    public static Object getRandomElementFromSet(Set set) {
        int setetSize = set.size();
        int item = randomInt(setetSize);
        int i = 0;
        for (Object obj : set) {
            if (i == item) {
                return obj;
            }
            i = i+1;
        }
        return new Object();
    }

    public static int randomInt(int maxValue) {
        return random.nextInt(maxValue);
    }
}
