package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {

    private Coordinate coordinate;

    @Before
    public void setUp() {
        coordinate = new Coordinate(0, 0, 0);
    }

    @Test
    public void testConstructor() {
        assertNotNull(coordinate);
        assertEquals(0, coordinate.getX(), 0.0);
        assertEquals(0, coordinate.getY(), 0.0);
        assertEquals(0, coordinate.getZ(), 0.0);
    }


    @Test
    public void testGetDistance() {
        Coordinate compareCoordinate1 = new Coordinate(4, 4, 0);
        Coordinate compareCoordinate2 = new Coordinate(0, -4, -4);

        Coordinate compareCoordinate3 = new Coordinate(0, -10, 0);

        assertEquals(Math.sqrt(32), coordinate.getDistance(compareCoordinate1), 0.0);
        assertEquals(Math.sqrt(32), coordinate.getDistance(compareCoordinate2), 0.0);
        assertEquals(10, coordinate.getDistance(compareCoordinate3), 0.0);
    }

    @Test
    public void testIsEqual() {
        Coordinate compareCoordinate1 = new Coordinate(0, 0, 0);
        Coordinate compareCoordinate2 = new Coordinate(0.1, 0, 0);

        assertTrue(coordinate.isEqual(coordinate));
        assertTrue(coordinate.isEqual(compareCoordinate1));
        assertFalse(coordinate.isEqual(compareCoordinate2));
    }
}
