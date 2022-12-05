package org.wahlzeit.model.coordinate;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.CoordinateType;

import static org.junit.Assert.*;

public class SharedCoordinateFactoryTest {

    private SharedCoordinateFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = SharedCoordinateFactory.getInstance();
    }

    @Test
    public void getCoordinate() {
        Coordinate coordinate = factory.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL);
        Coordinate coordinate1 = factory.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN);
        Coordinate coordinate2 = factory.getCoordinate(1,2,3, CoordinateType.CARTESIAN);
        Coordinate coordinate3 = factory.getCoordinate(1, 2, 3, CoordinateType.SPHERICAL);

        assertEquals(coordinate1, coordinate);
        assertNotEquals(coordinate1, coordinate2);
        assertNotEquals(coordinate, coordinate3);
        assertNotEquals(coordinate2, coordinate3);
    }
}