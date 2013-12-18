package fengfei.web.app.init;

import fengfei.forest.database.pool.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Play;
import play.PlayPlugin;
import play.db.DB;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PooledDBPlugin extends PlayPlugin {

    static Logger logger = LoggerFactory.getLogger(PooledDBPlugin.class);
    public static final String POOL_NAME = "database.pool.name";
    public static final String POOL_BONECP = "BoneCP";
    public static final String POOL_DBCP = "DBCP";
    public static final String POOL_TOMCAT_JDBC = "TomcatJDBC";

    PoolableDataSourceFactory poolableDataSourceFactory = null;

    @Override
    public void onApplicationStart() {

        DbParameters dp = readDbParameters();

        String poolName = dp.poolName;
        if (null == poolName || "".equals(poolName)) {
            poolName = "BoneCP";
        }

        if (POOL_BONECP.equalsIgnoreCase(poolName)) {
            poolableDataSourceFactory = new BonePoolableDataSourceFactory();
        } else if (POOL_DBCP.equalsIgnoreCase(poolName)) {
            poolableDataSourceFactory = new DbcpPoolableDataSourceFactory();
        } else if (POOL_TOMCAT_JDBC.equalsIgnoreCase(poolName)) {
            poolableDataSourceFactory = new TomcatPoolableDataSourceFactory();
        } else {
            logger.warn("Can't supported pool, default using TomcatJDBC.");
            poolableDataSourceFactory = new TomcatPoolableDataSourceFactory();
        }

        try {
            DB.datasource = poolableDataSourceFactory.createDataSource(
                dp.driver,
                dp.url,
                dp.user,
                dp.password,
                dp.params);
            logger.info(String.format("Initialized %s pool configuration.", poolName));
        } catch (PoolableException e) {
            logger.error("Initialize datasource error", e);
        }

    }

    @Override
    public void onApplicationStop() {
        try {
            poolableDataSourceFactory.destory(DB.datasource);
            logger.info(String.format("destory %s.", DB.datasource.getClass().getName()));
        } catch (PoolableException e) {
            logger.error("destory datasource error", e);
        }
    }

    private DbParameters readDbParameters() {

        Properties p = Play.configuration;

        String user = p.getProperty("database.user");
        String url = p.getProperty("database.url");
        String password = p.getProperty("database.password");
        String driver = p.getProperty("database.driver");
        String poolName = p.getProperty(POOL_NAME);
        Map<String, String> params = readPoolParams(p, poolName);
        DbParameters dbParameters = new DbParameters(url, driver, user, password, poolName, params);
        return dbParameters;
    }

    private Map<String, String> readPoolParams(Properties p, String keyPrefix) {
        Map<String, String> params = new HashMap<>();
        for (Entry<Object, Object> entry : p.entrySet()) {
            if (entry.getKey() instanceof String) {
                String key = entry.getKey().toString();
                if (key == null || !key.startsWith(keyPrefix)) {
                    continue;
                }
                String mapKey = StringUtils.substringAfterLast(key, ".");
                params.put(mapKey, entry.getValue().toString());

            } else {
                logger.warn("Unexpected non-string property key: " + entry.getKey());
            }
        }
        return params;
    }

    public static class DbParameters {

        public String url;
        public String driver;
        public String user;
        public String password;
        public String poolName;
        public Map<String, String> params;

        public DbParameters(
            String url,
            String driver,
            String user,
            String password,
            String poolName,
            Map<String, String> params) {
            super();
            this.url = url;
            this.driver = driver;
            this.user = user;
            this.password = password;
            this.poolName = poolName;
            this.params = params;
        }

    }
}
