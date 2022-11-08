package org.wahlzeit.model;

import java.util.function.DoublePredicate;

public class SphericalCoordinate implements Coordinate {

    private static final DoublePredicate PHI_PREDICATE = p -> p < 0 || p > Math.PI;
    private static final DoublePredicate THETA_PREDICATE = t -> t < 0 || t > 2 * Math.PI;
    private static final DoublePredicate RADIUS_PREDICATE = r -> r < 0;

    private double phi;

    private double theta;

    private double radius;

    public SphericalCoordinate(double phi, double theta, double radius) {
        checkInputs(phi, theta, radius);
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(
            radius * Math.sin(phi) * Math.cos(theta),
            radius * Math.sin(phi) * Math.sin(theta),
            radius * Math.cos(phi)
        );
    }

    @Override
    public SphericalCoordinate asSphericalCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return this.asCartesianCoordinate().getCartesianDistance(coordinate);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        return this.asCartesianCoordinate().getCentralAngle(coordinate);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return asCartesianCoordinate().isEqual(coordinate);
    }

    @Override
    public int hashCode() {
        return asCartesianCoordinate().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinate && isEqual((Coordinate) obj);
    }

    private void checkInputs(double phi, double theta, double radius) {
        checkInput(phi, PHI_PREDICATE, "phi");
        checkInput(theta, THETA_PREDICATE, "theta");
        checkInput(radius, RADIUS_PREDICATE, "radius");
    }

    private void checkInput(double input, DoublePredicate inputCheck, String inputName) {
        if (inputCheck.test(input)) {
            throw new IllegalArgumentException(inputName + " out of bounds");
        }
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        checkInput(phi, PHI_PREDICATE, "phi");
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        checkInput(theta, THETA_PREDICATE, "theta");
        this.theta = theta;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        checkInput(radius, RADIUS_PREDICATE, "radius");
        this.radius = radius;
    }
}
