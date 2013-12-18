package fengfei.ucm.dao.transducer;

import java.sql.ResultSet;
import java.sql.SQLException;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.relation.entity.Metadata;

public class MetadataTransducer implements Transducer<Metadata> {

	@Override
	public Metadata transform(ResultSet rs) throws SQLException {
		long sourceId = rs.getLong("source_id");
		int count = rs.getInt("count");
		String type = rs.getString("type");
		int state = rs.getInt("state");
		int createdAt = rs.getInt("created_at");
		long updatedAt = rs.getLong("updated_at");

		Metadata metadata = new Metadata(sourceId, type, count, state,
				updatedAt, createdAt);
		return metadata;
	}
}
