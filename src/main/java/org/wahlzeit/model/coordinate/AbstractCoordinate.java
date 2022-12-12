package org.wahlzeit.model.coordinate;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.utils.MathUtil;

import java.util.Objects;

public abstract class AbstractCoordinate implements Coordinate {

    protected static final double EQUALS_DELTA = 0.0009;

    /**
     * abstract method checking the invariants
     * Has to be implemented in concrete classes
     */
    protected abstract void assertClassInvariants();
    @Override
    @PatternInstance(
            patternName = "Template method",
            participants = {"CartesianCoordinate", "SphericalCoordinate"}
    )
    public double getCartesianDistance(Coordinate coordinate) {
        assertClassInvariants();
        assertCoordinateIsNotNull(coordinate);
        double distance = this.calculateEuclidianDistance(coordinate.asCartesianCoordinate());
        assertDoubleIsPositiveOrZero(distance);
        assertClassInvariants();

        return distance;
    }

    @Override
    @PatternInstance(
            patternName = "Template method",
            participants = {"CartesianCoordinate", "SphericalCoordinate"}
    )
    public double getCentralAngle(Coordinate coordinate) {
        assertClassInvariants();
        assertCoordinateIsNotNull(coordinate);
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        CartesianCoordinate currentCoordinate = this.asCartesianCoordinate();
        double centralAngle = Math.acos(
                MathUtil.dot(
                        new double[]{currentCoordinate.getX(), currentCoordinate.getY(), currentCoordinate.getZ()},
                        new double[]{cartesianCoordinate.getX(), cartesianCoordinate.getY(), cartesianCoordinate.getZ()}
                )
        );
        assertDoubleIsPositiveOrZero(centralAngle);
        assertClassInvariants();
        return centralAngle;
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        // can be done because the coordinates are shared values
        return this.hashCode() == coordinate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinate && isEqual((Coordinate) obj);
    }

    @Override
    @PatternInstance(
            patternName = "Template method",
            participants = {"CartesianCoordinate", "SphericalCoordinate"}
    )
    public int hashCode() {
        assertClassInvariants();
        CartesianCoordinate currentCoordinate = this.asCartesianCoordinate();

        int hash = Objects.hash(round(currentCoordinate.getX()), round(currentCoordinate.getY()), round(currentCoordinate.getZ()));

        assertClassInvariants();

        return hash;
    }
    private double calculateEuclidianDistance(CartesianCoordinate coordinate) {

        CartesianCoordinate currentCoordinate = this.asCartesianCoordinate();

        double distanceX = currentCoordinate.getX() - coordinate.getX();
        double distanceY = currentCoordinate.getY() - coordinate.getY();
        double distanceZ = currentCoordinate.getZ() - coordinate.getZ();

        return Math.sqrt(
                distanceX * distanceX +
                        distanceY * distanceY +
                        distanceZ * distanceZ
        );
    }

    private void assertCoordinateIsNotNull(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate can not be null");
        }
    }

    private void assertDoubleIsPositiveOrZero(double number) {
        if (number < 0) {
            throw new ArithmeticException("Result of method is smaller than zero");
        }
    }

    private double round(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
