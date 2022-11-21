package org.wahlzeit.model;

/**
 * Cartesian Coordinate.
 * No invariants necessary. All values for x, y and z are allowed
 */
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
    protected void assertClassInvariants() {
        // Do nothing. No state of the class fields violates any rule for cartesian coordinates
        // If later an implementation is necessary the method is already incorporated into the classes' method
        // Because the implementation is empty the compiler or Just In Time Compiler of Java should
        // be able to discard the calling of this method for Cartesian Coordinate at this moment.
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public SphericalCoordinate asSphericalCoordinate() {
        assertClassInvariants();
        SphericalCoordinate sphericalCoordinate = new SphericalCoordinate(
                Math.atan2(Math.sqrt(x * x + y * y), z),
                Math.atan2(y, x),
                Math.sqrt(x * x + y * y + z * z)
        );
        assert sphericalCoordinate.getPhi() >= 0 && sphericalCoordinate.getPhi() <= Math.PI;
        assert sphericalCoordinate.getTheta() >= 0 && sphericalCoordinate.getTheta() <= 2 * Math.PI;
        assert sphericalCoordinate.getRadius() >= 0;

        assertClassInvariants();
        return sphericalCoordinate;
    }

    /**
     * Return the x field
     * @return this.x
     */
    public double getX() {
        assertClassInvariants();
        return x;
    }

    /**
     * Sets the x field
     * @param x variable for the x coordinate
     */
    public void setX(double x) {
        assertClassInvariants();
        this.x = x;
        assertClassInvariants();
    }

    /**
     * Return the y field
     * @return this.y
     */
    public double getY() {
        assertClassInvariants();
        return y;
    }

    /**
     * Sets the x field
     * @param y variable for the y coordinate
     */
    public void setY(double y) {
        assertClassInvariants();
        this.y = y;
        assertClassInvariants();
    }

    /**
     * Return the z field
     * @return this.z
     */
    public double getZ() {
        assertClassInvariants();
        return z;
    }

    /**
     * Sets the x field
     * @param z variable for the z coordinate
     */
    public void setZ(double z) {
        assertClassInvariants();
        this.z = z;
        assertClassInvariants();
    }
}
