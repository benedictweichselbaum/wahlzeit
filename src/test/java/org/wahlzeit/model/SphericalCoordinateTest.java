package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.coordinate.CartesianCoordinate;
import org.wahlzeit.model.coordinate.Coordinate;
import org.wahlzeit.model.coordinate.SharedCoordinateFactory;
import org.wahlzeit.model.coordinate.SphericalCoordinate;

import static org.junit.Assert.*;

public class SphericalCoordinateTest {

    private SphericalCoordinate sphericalCoordinate;

    private SharedCoordinateFactory factory;

    @Before
    public void setUp() {
        factory = SharedCoordinateFactory.getInstance();
        sphericalCoordinate = factory.getCoordinate(Math.PI / 2, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_badCase_radius() {
        SphericalCoordinate coordinate = factory.getCoordinate(Math.PI / 2, Math.PI, -1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_badCase_phi() {
        SphericalCoordinate coordinate = factory.getCoordinate(4 * Math.PI, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_badCase_theta() {
        SphericalCoordinate coordinate = factory.getCoordinate(Math.PI / 2,4 * Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();
    }

    @Test
    public void testAsCartesianCoordinate() {
        CartesianCoordinate cartesianCoordinate = sphericalCoordinate.asCartesianCoordinate();

        assertEquals(-1.0, cartesianCoordinate.getX(), 0.0001);
        assertEquals(0, cartesianCoordinate.getY(), 0.0001);
        assertEquals(0, cartesianCoordinate.getZ(), 0.0001);
    }

    @Test
    public void testGetCartesianDistance() {
        Coordinate coordinate = factory.getCoordinate(1, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(2.0, sphericalCoordinate.getCartesianDistance(coordinate), 0.00001);
    }

    @Test
    public void testGetCentralAngle() {
        Coordinate coordinate = factory.getCoordinate(0, 0, 1, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(Math.PI / 2, sphericalCoordinate.getCentralAngle(coordinate), 0.0001);
    }

    @Test
    public void testIsEqual() {
        SphericalCoordinate sameCoord = factory.getCoordinate(Math.PI / 2, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();
        SphericalCoordinate almostSameCoord = factory.getCoordinate(Math.PI / 2, Math.PI, 1.000001, CoordinateType.SPHERICAL).asSphericalCoordinate();
        SphericalCoordinate notTheSameCoord = factory.getCoordinate(Math.PI / 2 + 0.5, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();

        assertTrue(sphericalCoordinate.isEqual(sameCoord));
        assertFalse(sphericalCoordinate.isEqual(almostSameCoord));
        assertFalse(sphericalCoordinate.isEqual(notTheSameCoord));
    }

    @Test
    public void testHashCode() {
        SphericalCoordinate sameCoord = factory.getCoordinate(Math.PI / 2, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();
        SphericalCoordinate almostSameCoord = factory.getCoordinate(Math.PI / 2, Math.PI, 1.000001, CoordinateType.SPHERICAL).asSphericalCoordinate();
        SphericalCoordinate notTheSameCoord = factory.getCoordinate(Math.PI / 2 + 0.5, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();

        assertEquals(sphericalCoordinate.hashCode(), sameCoord.hashCode());
        assertNotEquals(sphericalCoordinate.hashCode(), almostSameCoord.hashCode());
        assertNotEquals(sphericalCoordinate.hashCode(), notTheSameCoord.hashCode());
    }

    @Test
    public void testEquals() {
        Object someOtherObject = new Object();
        SphericalCoordinate sameCoord = factory.getCoordinate(Math.PI / 2, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();
        SphericalCoordinate notTheSameCoord = factory.getCoordinate(Math.PI / 2 + 0.5, Math.PI, 1.0, CoordinateType.SPHERICAL).asSphericalCoordinate();

        assertEquals(sphericalCoordinate, sameCoord);
        assertNotEquals(sphericalCoordinate, someOtherObject);
        assertNotEquals(sphericalCoordinate, notTheSameCoord);
    }
}