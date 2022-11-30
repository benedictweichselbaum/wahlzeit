package org.wahlzeit.model;

import org.wahlzeit.model.exceptions.LocationCreationException;
import org.wahlzeit.model.exceptions.NullArgumentException;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.SysLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location extends DataObject {

    protected Integer id = null;
    protected Coordinate coordinate;

    public Location(double a, double b, double c, CoordinateType coordinateType) throws LocationCreationException {
        try {
            this.coordinate = coordinateType == CoordinateType.CARTESIAN ? new CartesianCoordinate(a, b, c) : new SphericalCoordinate(a, b, c);
        } catch (IllegalArgumentException e) {
            SysLog.logThrowable(e);
            throw new LocationCreationException(e);
        }
        incWriteCount();
    }

    public Location(ResultSet rset) throws SQLException {
        readFrom(rset);
        incWriteCount();
    }

    public Location(Integer id, double a, double b, double c, CoordinateType coordinateType) throws LocationCreationException {
        this.id = id;
        try {
            this.coordinate = coordinateType == CoordinateType.CARTESIAN ? new CartesianCoordinate(a, b, c) : new SphericalCoordinate(a, b, c);
        } catch (IllegalArgumentException e) {
            SysLog.logThrowable(e);
            throw new LocationCreationException(e);
        }
        incWriteCount();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) throws NullArgumentException {
        if (coordinate != null) {
            this.coordinate = coordinate;
            incWriteCount();
        } else {
            throw new NullArgumentException("Coordinate can not be null");
        }
    }

    @Override
    public String getIdAsString() {
        return Integer.toString(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        coordinate = new CartesianCoordinate(
                rset.getDouble("location_x"),
                rset.getDouble("location_y"),
                rset.getDouble("location_z")
        );
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateDouble("location_x", coordinate.asCartesianCoordinate().getX());
        rset.updateDouble("location_y", coordinate.asCartesianCoordinate().getY());
        rset.updateDouble("location_z", coordinate.asCartesianCoordinate().getZ());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id);
    }

    public Integer getId() {
        return id;
    }
}
