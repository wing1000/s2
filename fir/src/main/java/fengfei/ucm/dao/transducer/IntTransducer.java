package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntTransducer implements Transducer<Integer> {

    @Override
    public Integer transform(ResultSet rs) throws SQLException {

        return rs.getInt(1);

    }
}
