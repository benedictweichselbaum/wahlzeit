package org.wahlzeit.utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MathUtil {

    private MathUtil() {

    }

    public static double dot(double[] vec1, double[] vec2) {
        if (vec1.length == vec2.length) {
            return IntStream.range(0, vec1.length).mapToDouble(i -> vec1[i] * vec2[i]).sum();
        } else {
            throw new IllegalArgumentException("Vectors have to be same size");
        }
    }
}
