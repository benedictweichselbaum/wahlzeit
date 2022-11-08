package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SphericalCoordinateTest {

    private SphericalCoordinate sphericalCoordinate;

    @Before
    public void setUp() {
        sphericalCoordinate = new SphericalCoordinate(Math.PI / 2, Math.PI, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_badCase_radius() {
        SphericalCoordinate coordinate = new SphericalCoordinate(Math.PI / 2, Math.PI, -1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_badCase_phi() {
        SphericalCoordinate coordinate = new SphericalCoordinate(4 * Math.PI, Math.PI, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_badCase_theta() {
        SphericalCoordinate coordinate = new SphericalCoordinate(Math.PI / 2,4 * Math.PI, 1.0);
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
        Coordinate coordinate = new CartesianCoordinate(1, 0, 0);

        assertEquals(2.0, sphericalCoordinate.getCartesianDistance(coordinate), 0.00001);
    }

    @Test
    public void testGetCentralAngle() {
        Coordinate coordinate = new CartesianCoordinate(0, 0, 1);

        assertEquals(Math.PI / 2, sphericalCoordinate.getCentralAngle(coordinate), 0.0001);
    }

    @Test
    public void testIsEqual() {
        SphericalCoordinate sameCoord = new SphericalCoordinate(Math.PI / 2, Math.PI, 1.0);
        SphericalCoordinate almostSameCoord = new SphericalCoordinate(Math.PI / 2, Math.PI, 1.000001);
        SphericalCoordinate notTheSameCoord = new SphericalCoordinate(Math.PI / 2 + 0.5, Math.PI, 1.0);

        assertTrue(sphericalCoordinate.isEqual(sameCoord));
        assertTrue(sphericalCoordinate.isEqual(almostSameCoord));
        assertFalse(sphericalCoordinate.isEqual(notTheSameCoord));
    }

    @Test
    public void testHashCode() {
        SphericalCoordinate sameCoord = new SphericalCoordinate(Math.PI / 2, Math.PI, 1.0);
        SphericalCoordinate almostSameCoord = new SphericalCoordinate(Math.PI / 2, Math.PI, 1.000001);
        SphericalCoordinate notTheSameCoord = new SphericalCoordinate(Math.PI / 2 + 0.5, Math.PI, 1.0);

        assertEquals(sphericalCoordinate.hashCode(), sameCoord.hashCode());
        assertEquals(sphericalCoordinate.hashCode(), almostSameCoord.hashCode());
        assertNotEquals(sphericalCoordinate.hashCode(), notTheSameCoord.hashCode());
    }

    @Test
    public void testEquals() {
        Object someOtherObject = new Object();
        SphericalCoordinate sameCoord = new SphericalCoordinate(Math.PI / 2, Math.PI, 1.0);
        SphericalCoordinate notTheSameCoord = new SphericalCoordinate(Math.PI / 2 + 0.5, Math.PI, 1.0);

        assertEquals(sphericalCoordinate, sameCoord);
        assertNotEquals(sphericalCoordinate, someOtherObject);
        assertNotEquals(sphericalCoordinate, notTheSameCoord);
    }
}