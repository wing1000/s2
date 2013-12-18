package fengfei.ucm.dao.transducer;

import java.sql.ResultSet;
import java.sql.SQLException;

import fengfei.forest.database.dbutils.Transducer;

public class IntTransducer implements Transducer<Integer> {

    @Override
    public Integer transform(ResultSet rs) throws SQLException {

        return rs.getInt(1);

    }
}
