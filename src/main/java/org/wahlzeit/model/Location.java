package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location extends DataObject {

    protected Integer id = null;
    protected Coordinate coordinate;

    public Location(double x, double y, double z) {
        this.coordinate = new Coordinate(x, y, z);
        incWriteCount();
    }

    public Location(ResultSet rset) throws SQLException {
        readFrom(rset);
        incWriteCount();
    }

    public Location(Integer id, double x, double y, double z) {
        this.id = id;
        this.coordinate = new Coordinate(x, y, z);
        incWriteCount();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        if (coordinate != null) {
            this.coordinate = coordinate;
            incWriteCount();
        } else {
            throw new IllegalArgumentException("coordinate can not be null");
        }
    }

    @Override
    public String getIdAsString() {
        return Integer.toString(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        coordinate = new Coordinate(
                rset.getDouble("location_x"),
                rset.getDouble("location_y"),
                rset.getDouble("location_z")
        );
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateDouble("location_x", coordinate.getX());
        rset.updateDouble("location_y", coordinate.getY());
        rset.updateDouble("location_z", coordinate.getZ());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id);
    }

    public Integer getId() {
        return id;
    }
}
