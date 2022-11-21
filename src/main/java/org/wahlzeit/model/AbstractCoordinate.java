package org.wahlzeit.model;

import org.wahlzeit.utils.MathUtil;

public abstract class AbstractCoordinate implements Coordinate {

    protected static final double EQUALS_DELTA = 0.0009;

    /**
     * abstract method checking the invariants
     * Has to be implemented in concrete classes
     */
    protected abstract void assertClassInvariants();
    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        assertClassInvariants();
        assertCoordinateIsNotNull(coordinate);
        double distance = this.calculateEuclidianDistance(coordinate.asCartesianCoordinate());
        assertDoubleIsPositiveOrZero(distance);
        assertClassInvariants();

        return distance;
    }

    @Override
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
        assertClassInvariants();
        assertCoordinateIsNotNull(coordinate);
        boolean isEqual = isEqualCartesian(coordinate.asCartesianCoordinate());
        assertClassInvariants();

        return isEqual;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinate && isEqual((Coordinate) obj);
    }

    @Override
    public int hashCode() {
        assertClassInvariants();
        CartesianCoordinate currentCoordinate = this.asCartesianCoordinate();

        long result = 120L;

        result = 37 * result + Double.doubleToLongBits(Math.round(currentCoordinate.getX() * 100.0) / 100.0);
        result = 37 * result + Double.doubleToLongBits(Math.round(currentCoordinate.getY() * 100.0) / 100.0);
        result = 37 * result + Double.doubleToLongBits(Math.round(currentCoordinate.getZ() * 100.0) / 100.0);

        assertClassInvariants();

        return (int) result;
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

    /**
     * Checks if coordinate is equal to a given one
     * @param coordinate given coordinate
     * @return true if equal, false else
     */
    private boolean isEqualCartesian(CartesianCoordinate coordinate) {
        CartesianCoordinate currentCoordinate = this.asCartesianCoordinate();

        return Math.abs(currentCoordinate.getX() - coordinate.getX()) <= EQUALS_DELTA &&
                Math.abs(currentCoordinate.getY() - coordinate.getY()) <= EQUALS_DELTA &&
                Math.abs(currentCoordinate.getZ() - coordinate.getZ()) <= EQUALS_DELTA;
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
}
