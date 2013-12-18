package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class Sequence {

    final static String SequenceUnitName = "Sequence";
    final static AtomicInteger nextSeq = new AtomicInteger();

    public static long next(final String table) throws Exception {

        final long seed = nextSeq.getAndIncrement();
        long id = Transactions.execute(
                SequenceUnitName,
                new Long(seed),
                Function.Write,
                new TaCallback<Long>() {

                    @Override
                    public Long execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = String.valueOf(seed % 2 + 1);
                        return SequenceDao.nextIdFromSequenceTable(grower, suffix, table);
                    }

                });

        return id;

    }

    public static boolean install(final long id, final String... tables)
            throws Exception {

        boolean created = Transactions.execute(
                SequenceUnitName,
                new Long(id),
                Function.Write,
                new TaCallback<Boolean>() {

                    @Override
                    public Boolean execute(ForestGrower grower, String suffix) throws SQLException {
                        int[] i = SequenceDao.createSequenceTable(grower, suffix, id, tables);
                        return true;
                    }

                });

        return created;

    }
}
