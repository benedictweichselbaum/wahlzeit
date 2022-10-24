package org.wahlzeit.model;

/**
 * Coordinate object for location
 */
public class Coordinate {

    protected double x;

    protected double y;

    protected double z;

    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the euclidian distance to a given coordinate
     * @param coordinate given coordinate
     * @return calculated distance
     */
    public double getDistance(Coordinate coordinate) {

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
    public boolean isEqual(Coordinate coordinate) {
        return this.getX() == coordinate.getX() &&
                this.getY() == coordinate.getY() &&
                this.getZ() == coordinate.getZ();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinate && isEqual((Coordinate) obj);
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
