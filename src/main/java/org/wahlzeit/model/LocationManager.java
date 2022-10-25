package org.wahlzeit.model;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.services.SysLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LocationManager extends ObjectManager {
    private static final int START_ID = 1;

    protected static final LocationManager instance = new LocationManager();

    protected Map<Integer, Location> locationCache = new HashMap<>();

    protected LocationManager() {
        initialize();
    }

    public Location getLocation(Integer id) {
        Location location = locationCache.get(id);
        if (location == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM locations WHERE id = ?");
                location = (Location) readObject(stmt, id);
            } catch (SQLException ex) {
                SysLog.logThrowable(ex);
            }
        }

        return location;
    }

    public void addLocation(Location location) {
        locationCache.put(location.getId(), location);
        try {
            PreparedStatement stmt1 = getReadingStatement("INSERT INTO locations(id) VALUES(?)");
            createObject(location, stmt1, location.getId());
            PreparedStatement stmt2 = getUpdatingStatement("SELECT * FROM locations WHERE id = ?");
            updateObject(location, stmt2);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    public void removeLocation(Location location) {
        locationCache.remove(location.getId());
        try {
            PreparedStatement stmt = getUpdatingStatement("SELECT * FROM locations WHERE id = ?");
            updateObject(location, stmt);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    private void initialize() {
        Collection<Location> loadCollection = new ArrayList<>();
        loadLocations(loadCollection);
        loadCollection.forEach(l -> locationCache.put(l.getId(), l));
    }

    private void loadLocations(Collection<Location> result) {
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM locations");
            readObjects(result, stmt);
        } catch (SQLException ex) {
            SysLog.logThrowable(ex);
        }

        SysLog.logSysInfo("loaded all locations");
    }

    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        return new Location(rset);
    }

    public Location createLocation(double x, double y, double z) {
        int maxId = locationCache.keySet().stream().mapToInt(i -> i).max().orElse(START_ID);
        Location newLocation = new Location(maxId + 1, x, y, z);
        addLocation(newLocation);
        return newLocation;
    }



    public static LocationManager getInstance() {
        return instance;
    }
}
