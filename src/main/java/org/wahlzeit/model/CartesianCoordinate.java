package org.wahlzeit.model;

import org.wahlzeit.utils.MathUtil;

public class CartesianCoordinate implements Coordinate {
    private static final double EQUALS_DELTA = 0.0009;

    protected double x;

    protected double y;

    protected double z;

    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericalCoordinate() {
        return new SphericCoordinate(
                Math.atan(Math.sqrt(x * x + y * y) / z),
                Math.atan(y / x),
                Math.sqrt(x * x + y * y + z * z)
        );
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return this.getDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        return Math.acos(
                MathUtil.dot(
                        new double[]{x, y, z},
                        new double[]{cartesianCoordinate.getX(), cartesianCoordinate.getY(), cartesianCoordinate.getZ()}
                )
        );
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return false;
    }

    /**
     * Calculates the euclidian distance to a given coordinate
     * @param coordinate given coordinate
     * @return calculated distance
     */
    private double getDistance(CartesianCoordinate coordinate) {

        double distanceX = this.getX() - coordinate.getX();
        double distanceY = this.getY() - coordinate.getY();
        double distanceZ = this.getZ() - coordinate.getZ();

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
    public boolean isEqual(CartesianCoordinate coordinate) {
        return Math.abs(this.getX() - coordinate.getX()) <= EQUALS_DELTA &&
                Math.abs(this.getY() - coordinate.getY()) <= EQUALS_DELTA &&
                Math.abs(this.getZ() - coordinate.getZ()) <= EQUALS_DELTA;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinate && isEqual((CartesianCoordinate) obj);
    }

    @Override
    public int hashCode() {
        long result = 120L;

        result = 37 * result + Double.doubleToLongBits(Math.round(getX() * 100.0) / 100.0);
        result = 37 * result + Double.doubleToLongBits(Math.round(getY() * 100.0) / 100.0);
        result = 37 * result + Double.doubleToLongBits(Math.round(getZ() * 100.0) / 100.0);

        return (int) result;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }


}
