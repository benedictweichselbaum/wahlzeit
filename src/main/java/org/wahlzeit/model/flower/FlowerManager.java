package org.wahlzeit.model.flower;

import org.wahlzeit.annotations.CollaborationBinding;
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

/**
 * public collaboration ConcreteObjectManager {
 *     public role ConcreteDataObject {
 *          // Gets managed by the ConcreteManager by storing the object with its ID as the key.
 *         Integer getId();
 *     }
 *
 *     public role ConcreteManager {
 *         // No methods. The Manager uses the ConcreteDataObjects without them using the Manager themselves.
 *         // The Manager is therefor a client of the DataObject.
 *     }
 * }
 */
@CollaborationBinding(
        binds = {"ConcreteObjectManager.ConcreteManager"}
)
public class FlowerManager extends ObjectManager {

    private static final int START_ID = 1;
    
    protected static final FlowerManager instance = new FlowerManager();
    
    protected Map<Integer, Flower> flowerCache = new HashMap<>();

    protected Map<Integer, FlowerType> typeCache = new HashMap<>();
    
    protected FlowerManager() {
        initialize();
    }
    
    public Flower getFlower(Integer id) {
        Flower flower = flowerCache.get(id);
        if (flower == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM flowers WHERE id = ?");
                flower = (Flower) readObject(stmt, id);
            } catch (SQLException | ClassCastException ex) {
                SysLog.logThrowable(ex);
            }
        }

        return flower;
    }
    
    public void addFlower(Flower flower) {
        flowerCache.put(flower.getId(), flower);
        try {
            PreparedStatement stmt1 = getReadingStatement("INSERT INTO flowers(id) VALUES(?)");
            createObject(flower, stmt1, flower.getId());
            PreparedStatement stmt2 = getUpdatingStatement("SELECT * FROM flowers WHERE id = ?");
            updateObject(flower, stmt2);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    public void removeFlower(Flower flower) {
        flowerCache.remove(flower.getId());
        try {
            PreparedStatement stmt = getUpdatingStatement("SELECT * FROM flowers WHERE id = ?");
            updateObject(flower, stmt);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    public FlowerType getFlowerType(Integer id) {
        FlowerType type = typeCache.get(id);
        if (type == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM flower_types WHERE id = ?");
                type = (FlowerType) readObject(stmt, id);
            } catch (SQLException | ClassCastException ex) {
                SysLog.logThrowable(ex);
            }
        }
        return type;
    }

    public void addFlowerType(FlowerType type) {
        typeCache.put(type.getId(), type);
        try {
            PreparedStatement stmt1 = getReadingStatement("INSERT INTO flower_types(id) VALUES(?)");
            createObject(type, stmt1, type.getId());
            PreparedStatement stmt2 = getUpdatingStatement("SELECT * FROM flower_types WHERE id = ?");
            updateObject(type, stmt2);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    public void removeFlowerType(FlowerType type) {
        flowerCache.remove(type.getId());
        try {
            PreparedStatement stmt = getUpdatingStatement("SELECT * FROM flower_types WHERE id = ?");
            updateObject(type, stmt);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }
    
    private void initialize() {
        Collection<Flower> loadCollection = new ArrayList<>();
        Collection<FlowerType> typeCollection = new ArrayList<>();
        loadFlowers(loadCollection);
        loadFlowerTypes(typeCollection);
        loadCollection.forEach(l -> flowerCache.put(l.getId(), l));
        typeCollection.forEach(t -> typeCache.put(t.getId(), t));
    }
    
    private void loadFlowers(Collection<Flower> result) {
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM flowers");
            readObjects(result, stmt);
        } catch (SQLException ex) {
            SysLog.logThrowable(ex);
        }

        SysLog.logSysInfo("loaded all flowers");
    }

    private void loadFlowerTypes(Collection<FlowerType> result) {
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM flower_types");
            readObjects(result, stmt);
        } catch (SQLException ex) {
            SysLog.logThrowable(ex);
        }

        SysLog.logSysInfo("loaded all flower_types");
    }

    /**
     * Method for creating a Flower object from a ResultSet. Calls the Flower constructor directly.
     * @param rset ResultSet from database
     * @return new Flower from ResultSet
     * @throws SQLException exception when handling the ResultSet wrong
     */
    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        return new Flower(rset);
    }

    public static FlowerManager getInstance() {
        return instance;
    }

    /**
     * Creates a Flower from a FlowerType name.
     * Calls private method for extracting the right type/creating right type,
     * calls the createInstance Method from the FlowerType object.
     * @param typeName type name as String
     * @return new Flower with type as stated in the typeName String.
     */
    public Flower createFlower(String typeName) {
        assert typeName != null;
        FlowerType type = getFlowerTypeFromName(typeName);
        Integer newId = flowerCache.keySet().stream().mapToInt(i -> i).max().orElse(START_ID) + 1;
        Flower result = type.createInstance(newId);
        addFlower(result);
        return result;
    }

    /**
     * Helper method for extracting a FlowerType based on the String name. Either creates a new type or gets the
     * existing one.
     * @param typeName type name as String
     * @return extracted FlowerType
     */
    private FlowerType getFlowerTypeFromName(String typeName) {
        FlowerType foundType = typeCache.values().stream().filter(type -> type.getName().equals(typeName)).findFirst().orElse(null);
        if (foundType == null) {
            Integer newId = typeCache.keySet().stream().mapToInt(i -> i).max().orElse(START_ID) + 1;
            foundType = new FlowerType(newId, typeName);
            addFlowerType(foundType);
        }
        return foundType;
    }
}
