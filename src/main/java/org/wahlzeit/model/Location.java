package org.wahlzeit.model;

public class Location {
    protected Coordinate coordinate;

    public Location(double x, double y, double z) {
        this.coordinate = new Coordinate(x, y, z);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
