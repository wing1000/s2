package fengfei.web.app.init;

import fengfei.forest.slice.config.Config;
import fengfei.forest.slice.config.SliceConfigReader;
import fengfei.forest.slice.config.xml.XmlSliceConfigReader;
import fengfei.forest.slice.config.zk.ZKSliceConfigReader;
import fengfei.forest.slice.database.DatabaseRouterFactory;
import fengfei.forest.slice.database.utils.Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Play;
import play.PlayPlugin;

import java.util.Properties;

public class SliceDBPlugin extends PlayPlugin {

    static Logger logger = LoggerFactory.getLogger(SliceDBPlugin.class);
    public static final String SliceConfig = "SliceConfig";

    DatabaseRouterFactory databaseRouterFactory;
    SliceConfigReader configReader;

    @Override
    public void onApplicationStart() {
        System.out.println("----------------------------");
        Properties p = Play.configuration;
        String sliceConfig = p.getProperty(SliceConfig);
        if (sliceConfig != null && !"".equals(sliceConfig)) {
            String[] scc = sliceConfig.split("/");
            System.out.println("scc: " + scc.length);
            String file = scc[1];
            if (scc[0].equals("file")) {
                configReader = new XmlSliceConfigReader(file);
            } else {
                configReader = new ZKSliceConfigReader(file);
            }
            try {

                logger.info("reading xml config..." + file);
                Config config = configReader.read("/root");
                logger.info("pasering config....");
                // System.out.println(config.toString());
                databaseRouterFactory = new DatabaseRouterFactory(config);
                logger.info("pasered config.");
                Transactions
                    .setDatabaseSliceGroupFactory(databaseRouterFactory, Play.mode.isDev());

            } catch (Throwable e) {
                logger.error("Initialize slice config error for file " + sliceConfig, e);
            }
        }
    }
    private void expressInstallTable() {

    }
    @Override
    public void onApplicationStop() {
        // try {
        // if (null != Transactions.databaseSliceGroupFactory) {
        // Map<String, PoolableSliceGroup<?>> groups =
        // Transactions.databaseSliceGroupFactory
        // .allPoolableSliceGroup();
        // for (Entry<String, PoolableSliceGroup<?>> entry : groups.entrySet())
        // {
        // PoolableSliceGroup<?> group = entry.getValue();
        // if (group != null) {
        // PoolableDataSourceFactory poolableDataSourceFactory = group
        // .getPoolableDataSourceFactory();
        // if (poolableDataSourceFactory != null) {
        // Map<String, DataSource> pooledDataSources = group
        // .allPooledDataSources();
        // for (Entry<String, DataSource> et : pooledDataSources.entrySet()) {
        // DataSource dataSource = et.getValue();
        // poolableDataSourceFactory.destory(dataSource);
        // }
        // }
        // }
        //
        // }
        // }
        //
        // logger.info(String.format("destory %s.",
        // Neaf.databaseSliceGroupFactory
        // .getClass()
        // .getName()));
        // } catch (PoolableException e) {
        // logger.error("destory datasource error", e);
        // }
    }
}
