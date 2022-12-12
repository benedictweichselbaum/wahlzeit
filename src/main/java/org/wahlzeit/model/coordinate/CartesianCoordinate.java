package org.wahlzeit.model.coordinate;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.model.CoordinateType;
import org.wahlzeit.utils.MathUtil;

import static org.wahlzeit.model.coordinate.SphericalCoordinate.PHI_PREDICATE;
import static org.wahlzeit.model.coordinate.SphericalCoordinate.RADIUS_PREDICATE;
import static org.wahlzeit.model.coordinate.SphericalCoordinate.THETA_PREDICATE;

/**
 * Cartesian Coordinate.
 * No invariants necessary. All values for x, y and z are allowed
 */
@PatternInstance(
        patternName = "(Shared) Value Object",
        participants = {"SharedCoordinateFactory"}
)
public class CartesianCoordinate extends AbstractCoordinate {

    protected final double x;

    protected final double y;

    protected final double z;

    CartesianCoordinate(double x, double y, double z) {
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
        double[] sphericalVector = MathUtil.cartesianToSpherical(new double[]{x, y, z});
        SphericalCoordinate sphericalCoordinate = (SphericalCoordinate) SharedCoordinateFactory.getInstance().getCoordinate(
                sphericalVector[0],
                sphericalVector[1],
                sphericalVector[2],
                CoordinateType.SPHERICAL
        );
        assert PHI_PREDICATE.test(sphericalCoordinate.getPhi());
        assert THETA_PREDICATE.test(sphericalCoordinate.getTheta());
        assert RADIUS_PREDICATE.test(sphericalCoordinate.getRadius());

        assertClassInvariants();
        return sphericalCoordinate;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new CartesianCoordinate(this.getX(), this.getY(), this.getZ());
        }
    }

    @Override
    public CoordinateType getType() {
        return CoordinateType.CARTESIAN;
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
     * @param xToSet variable for the x coordinate
     * @return new and modified coordinate
     */
    public CartesianCoordinate setX(double xToSet) {
        assertClassInvariants();
        return SharedCoordinateFactory.getInstance().getCoordinate(xToSet, this.getY(), this.getZ(), CoordinateType.CARTESIAN).asCartesianCoordinate();
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
     * @param yToSet variable for the y coordinate
     * @return new and modified coordinate
     */
    public CartesianCoordinate setY(double yToSet) {
        assertClassInvariants();
        return SharedCoordinateFactory.getInstance().getCoordinate(this.getX(), yToSet, this.getZ(), CoordinateType.CARTESIAN).asCartesianCoordinate();
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
     * @param zToSet variable for the z coordinate
     * @return new and modified coordinate
     */
    public CartesianCoordinate setZ(double zToSet) {
        assertClassInvariants();
        return SharedCoordinateFactory.getInstance().getCoordinate(this.getX(), this.getY(), zToSet, CoordinateType.CARTESIAN).asCartesianCoordinate();
    }
}
