package org.wahlzeit.model;

public class CartesianCoordinate extends AbstractCoordinate {

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
    public SphericalCoordinate asSphericalCoordinate() {
        return new SphericalCoordinate(
                Math.atan2(Math.sqrt(x * x + y * y), z),
                Math.atan2(y, x),
                Math.sqrt(x * x + y * y + z * z)
        );
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
