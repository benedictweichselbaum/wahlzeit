package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CoordinateTest {

    private Coordinate coordinate;

    @Before
    public void setUp() {
        coordinate = new Coordinate(1, 2, 3);
    }

    @Test
    public void testConstructor() {
        assertNotNull(coordinate);
        assertEquals(1, coordinate.getX(), 0.0);
        assertEquals(2, coordinate.getY(), 0.0);
        assertEquals(3, coordinate.getZ(), 0.0);
    }
}
