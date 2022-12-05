package org.wahlzeit.model.coordinate;

import org.wahlzeit.model.CoordinateType;

import java.util.HashMap;
import java.util.Map;

public class SharedCoordinateFactory {

    private static final SharedCoordinateFactory SHARED_COORDINATE_FACTORY = new SharedCoordinateFactory();

    private final Map<Integer, Coordinate> coordinateMap;

    private SharedCoordinateFactory() {
        // Hide constructor, so no factory can be created from outside
        coordinateMap = new HashMap<>();
    }

    public static SharedCoordinateFactory getInstance() {
        return SHARED_COORDINATE_FACTORY;
    }

    public Coordinate getCoordinate(double a, double b, double c, CoordinateType type) {
        Coordinate enteredCoordinate = createEnteredValueCoordinate(a, b, c, type);

        Coordinate existentCoordinate = coordinateMap.get(enteredCoordinate.hashCode());

        if (existentCoordinate == null) {
            coordinateMap.put(enteredCoordinate.hashCode(), enteredCoordinate);
            return enteredCoordinate;
        } else {
            return existentCoordinate;
        }
    }

    private Coordinate createEnteredValueCoordinate(double a, double b, double c, CoordinateType type) {
        switch (type) {
            case SPHERICAL:
                return new SphericalCoordinate(a, b, c);
            case CARTESIAN:
            default:
                return new CartesianCoordinate(a, b, c);
        }
    }
}
