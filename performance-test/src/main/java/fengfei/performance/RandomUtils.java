package fengfei.performance;

import java.util.Random;

public class RandomUtils {

    public static long randomLong(Random random, long min, long max) {
        long rndLong = random.nextLong();
        while (rndLong == Long.MIN_VALUE) {
            rndLong = random.nextLong();
        }
        rndLong = Math.abs(rndLong);

        long num = rndLong % (max - min);
        while (num == 0) {
            num = rndLong % (max - min);
        }
        return num;
    }

}
