package org.wahlzeit.model;

/**
 * Coordinate object for location
 */
public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();

    SphericCoordinate asSphericalCoordinate();

    double getCartesianDistance(Coordinate coordinate);

    double getCentralAngle(Coordinate coordinate);

    boolean isEqual(Coordinate coordinate);
}
