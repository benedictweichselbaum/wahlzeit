package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CartesianCoordinateTest {

    private CartesianCoordinate coordinate;

    @Before
    public void setUp() {
        coordinate = new CartesianCoordinate(0, 0, 0);
    }

    @Test
    public void testConstructor() {
        assertNotNull(coordinate);
        assertEquals(0, coordinate.getX(), 0.0);
        assertEquals(0, coordinate.getY(), 0.0);
        assertEquals(0, coordinate.getZ(), 0.0);
    }

    @Test
    public void testAsSphericalCoordinate() {
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(-1, 0, 0);
        SphericalCoordinate sphericalCoordinate = cartesianCoordinate.asSphericalCoordinate();

        assertEquals(1.0, sphericalCoordinate.getRadius(), 0.00001);
        assertEquals(Math.PI / 2, sphericalCoordinate.getPhi(), 0.00001);
        assertEquals(Math.PI, sphericalCoordinate.getTheta(), 0.00001);
    }

    @Test
    public void testGetCartesianDistance() {
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1, 1, 0);

        assertEquals(Math.sqrt(2), coordinate.getCartesianDistance(cartesianCoordinate), 0.00001);
    }

    @Test
    public void testGetCentralAngle() {
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0, 0, 1);

        assertEquals(Math.PI / 2, coordinate.getCentralAngle(cartesianCoordinate), 0.0001);
    }

    @Test
    public void testIsEqual() {
        Coordinate compareCoordinate1 = new CartesianCoordinate(0, 0, 0);
        Coordinate compareCoordinate2 = new CartesianCoordinate(0.1, 0, 0);
        Coordinate compareCoordinate3 = new CartesianCoordinate(0.00001, 0, 0);

        assertTrue(coordinate.isEqual(coordinate));
        assertTrue(coordinate.isEqual(compareCoordinate1));
        assertFalse(coordinate.isEqual(compareCoordinate2));
        assertTrue(coordinate.isEqual(compareCoordinate3));
    }

    @Test
    public void testEquals() {
        Object someOtherObject = new Object();
        Coordinate compareCoordinateEquals = new CartesianCoordinate(0, 0, 0);
        Coordinate compareCoordinateNotEquals = new CartesianCoordinate(1, 0 , 0);

        // assert(Not)Equals-Method uses the implemented equals()-Method
        assertNotEquals(coordinate, someOtherObject);
        assertEquals(coordinate, compareCoordinateEquals);
        assertNotEquals(coordinate, compareCoordinateNotEquals);
    }

    @Test
    public void testHashCode() {
        Coordinate compareCoordinate1 = new CartesianCoordinate(0, 0, 0);
        Coordinate compareCoordinate2 = new CartesianCoordinate(0.1, 0, 0);
        Coordinate compareCoordinate3 = new CartesianCoordinate(0.00001, 0, 0);

        assertNotEquals(compareCoordinate1.hashCode(), compareCoordinate2.hashCode());
        assertEquals(compareCoordinate1.hashCode(), compareCoordinate3.hashCode());
    }
}
