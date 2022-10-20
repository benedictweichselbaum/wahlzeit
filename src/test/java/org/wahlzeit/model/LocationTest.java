package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LocationTest {

    Location location;

    @Before
    public void setUp() {
        location = new Location(1, 1, 1);
    }

    @Test
    public void testConstructor() {
        assertNotNull(location);
        assertNotNull(location.coordinate);
    }
}
