package org.wahlzeit.model.coordinate;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.model.CoordinateType;
import org.wahlzeit.utils.MathUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@PatternInstance(
        patternName = "Flyweight",
        participants = {"Coordinate", "CartesianCoordinate", "SphericalCoordinate"}
)
public class SharedCoordinateFactory {

    private static final SharedCoordinateFactory SHARED_COORDINATE_FACTORY = new SharedCoordinateFactory();

    private final Map<Integer, List<Coordinate>> coordinateMap;

    private SharedCoordinateFactory() {
        // Hide constructor, so no factory can be created from outside
        coordinateMap = new HashMap<>();
    }

    public static SharedCoordinateFactory getInstance() {
        return SHARED_COORDINATE_FACTORY;
    }

    public synchronized Coordinate getCoordinate(double a, double b, double c, CoordinateType type) {
        Coordinate enteredCoordinate = createEnteredValueCoordinate(a, b, c, type);

        List<Coordinate> existentCoordinate = coordinateMap.get(enteredCoordinate.hashCode());

        if (existentCoordinate == null) {
            coordinateMap.put(enteredCoordinate.hashCode(), Arrays.asList(enteredCoordinate, createComplementingCoordinate(a, b, c, type)));
            return enteredCoordinate;
        } else {
            Optional<Coordinate> optionalCoordinate = existentCoordinate.stream().filter(co -> co.getType() == type).findFirst();
            if (optionalCoordinate.isPresent()) {
                return optionalCoordinate.get();
            } else {
                coordinateMap.put(enteredCoordinate.hashCode(), Arrays.asList(enteredCoordinate, createComplementingCoordinate(a, b, c, type)));
                return enteredCoordinate;
            }
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
    
    private Coordinate createComplementingCoordinate(double a, double b, double c, CoordinateType existingType) {
        double[] inputVector = new double[]{a, b, c};
        switch (existingType) {
            case SPHERICAL:
                double[] cartesianVector = MathUtil.sphericalToCartesian(inputVector);
                return new CartesianCoordinate(
                        cartesianVector[0],
                        cartesianVector[1],
                        cartesianVector[2]
                );
            case CARTESIAN:
            default:
                double[] sphericalVector = MathUtil.cartesianToSpherical(inputVector);
                return new SphericalCoordinate(
                        sphericalVector[0],
                        sphericalVector[1],
                        sphericalVector[2]
                );
        }
    }
}
