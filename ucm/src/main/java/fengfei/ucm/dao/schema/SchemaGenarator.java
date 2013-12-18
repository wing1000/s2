package fengfei.ucm.dao.schema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaGenarator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int shardNum = 2;
        String ip = "localhost";
        int port = 3306;
        String database = "relation";
        String uname = "root";
        String pwd = "";
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, uname, pwd);
            for (int i = 0; i < shardNum; i++) {
                System.out.println("start create schema for shard: " + (i + 1));
                System.out.println();
                createSchema(connection, database, (i + 1) + "_following", true);
                createSchema(connection, database, (i + 1) + "_followed", true);
                System.out.println();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    private static void createSchema(
        Connection connection,
        String database,
        String suffix,
        boolean isDropTable) throws SQLException {
        Statement statement = connection.createStatement();

        String metadataTable = "metadata_" + suffix;
        String relationTable = "rs_" + suffix;
        System.out.println();
        if (isDropTable) {
            statement.execute("DROP TABLE IF EXISTS " + database + "." + metadataTable);
            System.out.println("        drop table " + metadataTable);
            statement.execute("DROP TABLE IF EXISTS " + database + "." + relationTable);
            System.out.println("        drop table " + relationTable);
        }
        statement.executeUpdate(String.format(metadata, metadataTable));
        System.out.println("        create table " + metadataTable);
        statement.executeUpdate(String.format(relation, relationTable));
        System.out.println("        create table " + relationTable);

    }

    private static Connection connection;
    private static String metadata = " CREATE TABLE IF NOT EXISTS `%s` (   "
            + "   `source_id` int(10) unsigned NOT NULL,     "
            + "   `type` tinyint(1) NOT NULL,    " + "   `count` int(10) unsigned NOT NULL,     "
            + "   `state` tinyint(4) NOT NULL,   "
            + "   `updated_at` bigint(20) unsigned NOT NULL,     "
            + "   `created_at` int(10) unsigned NOT NULL,    "
            + "   PRIMARY KEY (`source_id`,`type`)   "
            + " ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction'   ";

    private static String relation = "    CREATE TABLE IF NOT EXISTS  `%s` (    "
            + "   `source_id` int(10) unsigned NOT NULL,     "
            + "   `target_id` int(10) unsigned NOT NULL,     "
            + "   `type` tinyint(1) NOT NULL,    " + "   `state` tinyint(4) NOT NULL,   "
            + "   `created_at` int(10) unsigned NOT NULL,    "
            + "   `updated_at` bigint(20) unsigned NOT NULL,     "
            + "   PRIMARY KEY (`source_id`,`target_id`,`type`)   "
            + " ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='negative direction';     ";

}
