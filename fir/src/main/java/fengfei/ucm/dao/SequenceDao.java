package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class SequenceDao {

    final static Logger log = LoggerFactory.getLogger(SequenceDao.class);
    public final static String Sequence = "CREATE TABLE IF NOT EXISTS `Seq64_%s` ( \n"
            + "  `id` bigint(20) unsigned NOT NULL auto_increment, \n"
            + "  `stub` char(1) NOT NULL default '', \n" + "  PRIMARY KEY  (`id`), \n"
            + "  UNIQUE KEY `stub` (`stub`) \n" + ") ENGINE=MyISAM AUTO_INCREMENT=%s";

    public static long nextIdFromSequence(ForestGrower grower, String suffix, String table)
            throws SQLException {
        String update = "REPLACE INTO Seq64_%s(stub) VALUES ('a')";
        String sql = "SELECT LAST_INSERT_ID()";
        int updated = grower.update(String.format(update, table));
        long next = grower.selectOne(sql, new LongTransducer());
        return next;
    }

    public static int[] createSequence(ForestGrower grower, String suffix, String... tables)
            throws SQLException {
        int[] rs = new int[tables.length];
        for (int i = 0; i < rs.length; i++) {
            int updated = grower.update(String.format(Sequence, tables[i]));
            rs[i] = updated;
        }

        return rs;
    }

    //

    public final static String SequenceTable =
            "CREATE TABLE IF NOT EXISTS `idtb64%s` ("
                    + "  `id` bigint(20) unsigned NOT NULL default 0 ,"
                    + "  `table` varchar(32) NOT NULL,"
                    + "  PRIMARY KEY (`table`)"
                    + ") ENGINE=InnoDB ";

    public static long nextIdFromSequenceTable(ForestGrower grower, String suffix, String table)
            throws SQLException {
        String update = "UPDATE `idtb64_%s` SET `id` = LAST_INSERT_ID(`id` + 2) where `table`=?";
        String sql = "SELECT LAST_INSERT_ID()";
        int updated = grower.update(String.format(update, suffix), table);
        long next = grower.selectOne(sql, new LongTransducer());
        return next;
    }

    public static int[] createSequenceTable(
            ForestGrower grower,
            String suffix,
            long seq,
            String... tables) throws SQLException {

        //create table if not exists
        int updated = grower.update(String.format(SequenceTable, suffix));
        if (updated > 0) {
            log.info(String.format("Created sequence table idtb64%s.", suffix));
        }
        String sql = "select id from `idtb64%s` where `table`=? for update";

        String insert = "INSERT INTO `idtb64%s`(`table`,`id`) values(?,?)";
        int[] rs = new int[tables.length];
        for (int i = 0; i < tables.length; i++) {

            Long id = grower
                    .selectOne(String.format(sql, suffix), new LongTransducer(), tables[i]);
            if (id == null) {
                int inserted = grower.update(String.format(insert, suffix), tables[i], seq);
                log.info("Inited sequence table " + tables[i]);
            }
            rs[i] = updated;
        }

        return rs;
    }

}
