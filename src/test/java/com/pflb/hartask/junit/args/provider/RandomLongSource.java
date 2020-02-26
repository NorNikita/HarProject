package com.pflb.hartask.junit.args.provider;

public class RandomLongSource {
    public static Long randomLong() {
        return (long) (Math.random() * 100000 + 4);
    }
}
