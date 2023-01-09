package org.wahlzeit.model.flower;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Flower extends DataObject {

    private Integer id = null;

    private FlowerType type;

    protected Flower(Integer id, FlowerType type) {
        this.id = id;
        this.type = type;
        incWriteCount();
    }
    
    protected Flower (ResultSet rset) throws SQLException {
        readFrom(rset);
        incWriteCount();
    }

    public FlowerType getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        type = FlowerManager.getInstance().getFlowerType(rset.getInt("flower_type"));
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateInt("flower_type", type.getId());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id);
    }
}
