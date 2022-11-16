package org.wahlzeit.model;

import org.wahlzeit.utils.MathUtil;

public abstract class AbstractCoordinate implements Coordinate {

    protected static final double EQUALS_DELTA = 0.0009;
    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return this.calculateEuclidianDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        CartesianCoordinate currentCoordinate = this.asCartesianCoordinate();
        return Math.acos(
                MathUtil.dot(
                        new double[]{currentCoordinate.getX(), currentCoordinate.getY(), currentCoordinate.getZ()},
                        new double[]{cartesianCoordinate.getX(), cartesianCoordinate.getY(), cartesianCoordinate.getZ()}
                )
        );
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return isEqualCartesian(coordinate.asCartesianCoordinate());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinate && isEqual((Coordinate) obj);
    }

    @Override
    public int hashCode() {
        CartesianCoordinate currentCoordinate = this.asCartesianCoordinate();

        long result = 120L;

        result = 37 * result + Double.doubleToLongBits(Math.round(currentCoordinate.getX() * 100.0) / 100.0);
        result = 37 * result + Double.doubleToLongBits(Math.round(currentCoordinate.getY() * 100.0) / 100.0);
        result = 37 * result + Double.doubleToLongBits(Math.round(currentCoordinate.getZ() * 100.0) / 100.0);

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
}
