package org.wahlzeit.model.coordinate;

import org.wahlzeit.model.CoordinateType;
import org.wahlzeit.utils.MathUtil;

import java.util.function.DoublePredicate;

/**
 * @invariant phi >= 0 && phi <= PI
 * @invariant theta >= 0 && theta <= 2 * PI
 * @invariant radius >= 0
 */
public class SphericalCoordinate extends AbstractCoordinate {

    public static final DoublePredicate PHI_PREDICATE = p -> p >= 0 && p <= Math.PI;
    public static final DoublePredicate THETA_PREDICATE = t -> t >= 0 && t <= 2 * Math.PI;
    public static final DoublePredicate RADIUS_PREDICATE = r -> r >= 0;

    protected final double phi;

    protected final double theta;

    protected final double radius;

    SphericalCoordinate(double phi, double theta, double radius) {
        assertAllAttributesAreInRange(phi, theta, radius, true);
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
        assertClassInvariants();
    }

    @Override
    protected void assertClassInvariants() {
        assertAllAttributesAreInRange(phi, theta, radius, false);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        double[] cartesianVector = MathUtil.sphericalToCartesian(new double[]{phi, theta, radius});
        CartesianCoordinate coordinate = (CartesianCoordinate) SharedCoordinateFactory.getInstance().getCoordinate(
                cartesianVector[0],
                cartesianVector[1],
                cartesianVector[2],
                CoordinateType.CARTESIAN
        );
        assertClassInvariants();
        return coordinate;
    }

    @Override
    public SphericalCoordinate asSphericalCoordinate() {
        assertClassInvariants();
        // The checking of the class invariants also checks the postcondition for returned object
        // defined in the interface.
        return this;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new SphericalCoordinate(this.getPhi(), this.getTheta(), this.getRadius());
        }
    }

    @Override
    public CoordinateType getType() {
        return CoordinateType.SPHERICAL;
    }

    private void assertAllAttributesAreInRange(double phi, double theta, double radius, boolean asArgument) {
        assertAttributeIsInRange(phi, PHI_PREDICATE, "phi", asArgument);
        assertAttributeIsInRange(theta, THETA_PREDICATE, "theta", asArgument);
        assertAttributeIsInRange(radius, RADIUS_PREDICATE, "radius", asArgument);
    }

    private void assertAttributeIsInRange(double input, DoublePredicate inputCheck, String inputName, boolean asArgument) {
        if (!inputCheck.test(input)) {
            if (asArgument) {
                throw new IllegalArgumentException(inputName + " out of bounds");
            } else {
                throw new IllegalStateException(inputName + " out of bounds; object in illegal state");
            }
        }
    }

    /**
     * Returns the phi field
     * @post phi >= 0 && phi <= PI
     * @return the field phi
     */
    public double getPhi() {
        assertClassInvariants();
        // The checking of the class invariants also checks the postcondition for returned object
        return phi;
    }

    /**
     * sets the phi field in the object
     * @pre phi >= 0 && phi <= PI
     * @param phiToSet the inserted phi value
     * @return new and modified coordinate
     */
    public SphericalCoordinate setPhi(double phiToSet) {
        assertClassInvariants();
        assertAttributeIsInRange(phiToSet, PHI_PREDICATE, "phi", true);
        return SharedCoordinateFactory.getInstance().getCoordinate(phiToSet, this.getTheta(), this.getRadius(), CoordinateType.SPHERICAL).asSphericalCoordinate();
    }

    /**
     * Return the theta field
     * @post theta >= 0 && theta <= 2 * PI
     * @return the field theta
     */
    public double getTheta() {
        assertClassInvariants();
        // The checking of the class invariants also checks the postcondition for returned object
        return theta;
    }

    /**
     * sets the theta field in the object
     * @pre theta >= 0 && theta <= 2 * PI
     * @param thetaToSet the inserted phi value
     * @return new and modified coordinate
     */
    public SphericalCoordinate setTheta(double thetaToSet) {
        assertClassInvariants();
        assertAttributeIsInRange(thetaToSet, THETA_PREDICATE, "theta", true);
        return SharedCoordinateFactory.getInstance().getCoordinate(this.getPhi(), thetaToSet, this.getRadius(), CoordinateType.SPHERICAL).asSphericalCoordinate();
    }

    /**
     * Returns the radius
     * @post radius >= 0
     * @return the radius component of the coordinate
     */
    public double getRadius() {
        assertClassInvariants();
        // The checking of the class invariants also checks the postcondition for returned object
        return radius;
    }

    /**
     * sets the radius field in the object
     * @pre radius >= 0
     * @param radiusToSet the inserted radius value
     * @return new and modified coordinate
     */
    public SphericalCoordinate setRadius(double radiusToSet) {
        assertClassInvariants();
        assertAttributeIsInRange(radiusToSet, RADIUS_PREDICATE, "radius", true);
        return SharedCoordinateFactory.getInstance().getCoordinate(getPhi(), getTheta(), radiusToSet, CoordinateType.SPHERICAL).asSphericalCoordinate();
    }
}
