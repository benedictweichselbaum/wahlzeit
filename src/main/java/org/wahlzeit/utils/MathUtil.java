package org.wahlzeit.utils;

import org.wahlzeit.model.coordinate.SphericalCoordinate;

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

    /**
     * @pre Input vector has length 3 and is in range
     * Converts a spherical to a cartesian coordinate
     * @return vector with the 3 parameters
     */
    public static double[] sphericalToCartesian(double[] input) {
        checkSphericalVector(input);
        return new double[]{
                input[2] * Math.sin(input[0]) * Math.cos(input[1]),
                input[2] * Math.sin(input[0]) * Math.sin(input[1]),
                input[2] * Math.cos(input[0])
        };
    }
    
    public static double[] cartesianToSpherical(double[] input) {
        assert input.length == 3;
        double[] sphericalVector = new double[] {
                Math.atan2(Math.sqrt(input[0] * input[0] + input[1] * input[1]), input[2]),
                Math.atan2(input[1], input[0]),
                Math.sqrt(input[0] * input[0] + input[1] * input[1] + input[2] * input[2])
        };
        checkSphericalVector(sphericalVector);
        return sphericalVector;
    }
    
    private static void checkSphericalVector(double[] input) {
        if (!(input.length == 3 &&
                SphericalCoordinate.PHI_PREDICATE.test(input[0]) &&
                SphericalCoordinate.THETA_PREDICATE.test(input[1]) &&
                SphericalCoordinate.RADIUS_PREDICATE.test(input[2]))) {
            throw new IllegalArgumentException("Invalid spherical vector");
        }
    }
}
