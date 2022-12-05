package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.coordinate.CartesianCoordinate;
import org.wahlzeit.model.coordinate.Coordinate;
import org.wahlzeit.model.coordinate.SharedCoordinateFactory;
import org.wahlzeit.model.coordinate.SphericalCoordinate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CartesianCoordinateTest {

    private CartesianCoordinate coordinate;

    private SharedCoordinateFactory factory;

    @Before
    public void setUp() {
        factory = SharedCoordinateFactory.getInstance();
        coordinate = factory.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
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
        CartesianCoordinate cartesianCoordinate = factory.getCoordinate(-1, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
        SphericalCoordinate sphericalCoordinate = cartesianCoordinate.asSphericalCoordinate();

        assertEquals(1.0, sphericalCoordinate.getRadius(), 0.00001);
        assertEquals(Math.PI / 2, sphericalCoordinate.getPhi(), 0.00001);
        assertEquals(Math.PI, sphericalCoordinate.getTheta(), 0.00001);
    }

    @Test
    public void testGetCartesianDistance() {
        CartesianCoordinate cartesianCoordinate = factory.getCoordinate(1, 1, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(Math.sqrt(2), coordinate.getCartesianDistance(cartesianCoordinate), 0.00001);
    }

    @Test
    public void testGetCentralAngle() {
        CartesianCoordinate cartesianCoordinate = factory.getCoordinate(0, 0, 1, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(Math.PI / 2, coordinate.getCentralAngle(cartesianCoordinate), 0.0001);
    }

    @Test
    public void testIsEqual() {
        Coordinate compareCoordinate1 = factory.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
        Coordinate compareCoordinate2 = factory.getCoordinate(0.1, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
        Coordinate compareCoordinate3 = factory.getCoordinate(0.00001, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertTrue(coordinate.isEqual(coordinate));
        assertTrue(coordinate.isEqual(compareCoordinate1));
        assertFalse(coordinate.isEqual(compareCoordinate2));
        assertFalse(coordinate.isEqual(compareCoordinate3));
    }

    @Test
    public void testEquals() {
        Object someOtherObject = new Object();
        Coordinate compareCoordinateEquals = factory.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
        Coordinate compareCoordinateNotEquals = factory.getCoordinate(2, 20, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();

        // assert(Not)Equals-Method uses the implemented equals()-Method
        assertNotEquals(coordinate, someOtherObject);
        assertEquals(coordinate, compareCoordinateEquals);
        assertNotEquals(coordinate, compareCoordinateNotEquals);
    }

    @Test
    public void testHashCode() {
        Coordinate compareCoordinate1 = factory.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
        Coordinate compareCoordinate2 = factory.getCoordinate(0.1, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
        Coordinate compareCoordinate3 = factory.getCoordinate(0.00001, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertNotEquals(compareCoordinate1.hashCode(), compareCoordinate2.hashCode());
        assertNotEquals(compareCoordinate1.hashCode(), compareCoordinate3.hashCode());
    }
}
