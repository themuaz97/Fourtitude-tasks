package com.fourtitude;

class Lcg {
    private final long modulus;
    private final int multiplier;
    private final int increment;
    private long seed;

    public Lcg(long modulus, int multiplier, int increment, long seed) {
        this.modulus = modulus;
        this.multiplier = multiplier;
        this.increment = increment;
        this.seed = seed;
    }

    // @NotThreadSafe
    public /* synchronized */ long next() {
        // Y = (a.X + c) mod m
        long val = (multiplier * seed) + increment;
        seed = val % modulus;
        return seed;
    }

    public boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        for (long i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public long nthPrime(int n) {
        int count = 0;
        long currentNumber = seed;

        while (count < n) {
            currentNumber = next();
            if (isPrime(currentNumber)) {
                count++;
            }
        }

        return currentNumber;
    }
}

public class Task3Constructor {

}
