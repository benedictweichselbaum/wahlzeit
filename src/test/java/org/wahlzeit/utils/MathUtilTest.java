package org.wahlzeit.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathUtilTest {

    @Test
    public void testDot() {
        assertEquals(1.0, MathUtil.dot(new double[]{0, 0, 1}, new double[]{0, 0, 1}), 0.0001);
        assertEquals(-1.0, MathUtil.dot(new double[]{0, 0, 1}, new double[]{0, 0, -1}), 0.0001);
        assertEquals(0, MathUtil.dot(new double[]{0, 0, 1}, new double[]{0, 0, 0}), 0.0001);
    }
}
