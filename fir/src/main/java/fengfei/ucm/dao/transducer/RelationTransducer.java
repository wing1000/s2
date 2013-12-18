package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.relation.Relation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelationTransducer implements Transducer<Relation> {

    @Override
    public Relation transform(ResultSet rs) throws SQLException {
        long sourceId = rs.getLong("source_id");
        long targetId = rs.getLong("target_id");
        byte type = rs.getByte("type");
        int state = rs.getInt("state");
        int createdAt = rs.getInt("created_at");
        long updatedAt = rs.getLong("updated_at");
        long attachmentId = rs.getLong("attachment_id");

        Relation relation = new Relation(sourceId, targetId, type, state, updatedAt, createdAt);
        relation.attachmentId = attachmentId;
        return relation;
    }
}
