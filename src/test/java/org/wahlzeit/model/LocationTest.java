package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.exceptions.NullArgumentException;

import static org.junit.Assert.assertNotNull;

public class LocationTest {

    Location location;

    @Before
    public void setUp() throws Exception {
        location = new Location(1, 1, 1, CoordinateType.CARTESIAN);
    }

    @Test
    public void testConstructor() {
        assertNotNull(location);
        assertNotNull(location.coordinate);
    }

    @Test(expected = NullArgumentException.class)
    public void testSetCoordinate_withNull() throws Exception {
        location.setCoordinate(null);
    }
}
