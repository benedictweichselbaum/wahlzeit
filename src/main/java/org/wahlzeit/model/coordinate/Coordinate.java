package org.wahlzeit.model.coordinate;

import org.wahlzeit.model.CoordinateType;

/**
 * Coordinate interface for location object
 */
public interface Coordinate extends Cloneable {

    /**
     * Returns a cartesian representation of the coordinate
     * @return coordinate in cartesian representation
     */
    CartesianCoordinate asCartesianCoordinate();

    /**
     * Returns a spherical representation of the coordinate
     * @post spherical = returned object
     *        spherical.phi >= 0 && spherical.phi <= PI
     *        spherical.theta >= 0 && spherical.theta <= 2 * PI
     *        spherical.radius >= 0
     * @return a spherical representation of the coordinate
     */
    SphericalCoordinate asSphericalCoordinate();

    /**
     * Calculates the cartesian distance between the called and the inserted coordinate object
     * @param coordinate coordinate to which the distance is measured
     * @pre coordinate != null
     * @post return >= 0
     * @return distance between the two coordinates
     */
    double getCartesianDistance(Coordinate coordinate);

    /**
     * Calculates the central angle between two coordinates (called and inserted)
     * @param coordinate coordinate to which the central angle is measured
     * @pre coordinate != null
     * @post return >= 0
     * @return central angle between the two coordinates
     */
    double getCentralAngle(Coordinate coordinate);

    /**
     * Checks if two coordinates are equal with respect to a certain margin of error
     * @param coordinate to check if it is equal to called object
     * @pre coordinate != null
     * @post return = true if equal, return = false if not equal (according to definition)
     * @return true if equal according to definition (DELTA), false else
     */
    boolean isEqual(Coordinate coordinate);

    CoordinateType getType();
}
