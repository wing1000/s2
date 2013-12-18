package fengfei.ucm.dao.transducer;

import java.sql.ResultSet;
import java.sql.SQLException;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.relation.entity.Relation;

public class RelationTransducer implements Transducer<Relation> {



	@Override
	public Relation transform(ResultSet rs) throws SQLException {
		long sourceId = rs.getLong("source_id");
		long targetId = rs.getLong("target_id");
		byte type = rs.getByte("type");
		int state = rs.getInt("state");
		int createdAt = rs.getInt("created_at");
		long updatedAt = rs.getLong("updated_at");

		Relation relation = new Relation(sourceId, targetId, type, state,
				updatedAt, createdAt);

		return relation;
	}
}
