package org.wahlzeit.model;

import java.util.function.DoublePredicate;

/**
 * @invariant phi >= 0 && phi <= PI
 * @invariant theta >= 0 && theta <= 2 * PI
 * @invariant radius >= 0
 */
public class SphericalCoordinate extends AbstractCoordinate {

    static final DoublePredicate PHI_PREDICATE = p -> p >= 0 && p <= Math.PI;
    static final DoublePredicate THETA_PREDICATE = t -> t >= 0 && t <= 2 * Math.PI;
    static final DoublePredicate RADIUS_PREDICATE = r -> r >= 0;

    private double phi;

    private double theta;

    private double radius;

    public SphericalCoordinate(double phi, double theta, double radius) {
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
        CartesianCoordinate coordinate = new CartesianCoordinate(
            radius * Math.sin(phi) * Math.cos(theta),
            radius * Math.sin(phi) * Math.sin(theta),
            radius * Math.cos(phi)
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
     * @param phi the inserted phi value
     */
    public void setPhi(double phi) {
        assertClassInvariants();
        assertAttributeIsInRange(phi, PHI_PREDICATE, "phi", true);
        this.phi = phi;
        assertClassInvariants();
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
     * @param theta the inserted phi value
     */
    public void setTheta(double theta) {
        assertClassInvariants();
        assertAttributeIsInRange(theta, THETA_PREDICATE, "theta", true);
        this.theta = theta;
        assertClassInvariants();
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
     * @param radius the inserted radius value
     */
    public void setRadius(double radius) {
        assertClassInvariants();
        assertAttributeIsInRange(radius, RADIUS_PREDICATE, "radius", true);
        this.radius = radius;
        assertClassInvariants();
    }
}
