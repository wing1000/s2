package fengfei.performance.jdbcteset;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.protocol.java.test.JavaTest;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomcatJdbcTest extends JavaTest {

    private static final Logger logger = LoggerFactory.getLogger(TomcatJdbcTest.class);
    private static final long serialVersionUID = 1L;

    private static DataSource tomcatDatasource;
    private static PoolingDataSource dbcpDataSource;
    private String operation;
    private static AtomicLong idSeq = new AtomicLong(1);

    private Connection getConnection() throws SQLException {
        if (tomcatDatasource != null) {
            return tomcatDatasource.getConnection();
        } else {
            return dbcpDataSource.getConnection();
        }
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = new Arguments();
        args.addArgument("poolType", "tomcat");// tomcat or dbcp
        args.addArgument("maxActive", "600");
        args.addArgument("url", "jdbc:mysql://172.17.20.72:3306/relationdb_edges");
        args.addArgument("username", "lj");
        args.addArgument("password", "livejournal");
        args.addArgument("operation(create/query)", "query");
        return args;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sampleResult = new SampleResult();
        sampleResult.sampleStart();

        boolean success = true;
        try {
            Connection conn = getConnection();
            Dao dao = new Dao(conn);
            if (operation.equalsIgnoreCase("query")) {
                String r = dao.query();
                if (!r.equals("1")) {
                    success = false;
                }
            } else if (operation.equalsIgnoreCase("create")) {
                long id = idSeq.getAndIncrement();
                if (dao.create(id) != 1) {
                    success = false;
                }
            }
            conn.close();

        } catch (Exception e) {
            success = false;
            logger.error("test case exception", e);
        }

        sampleResult.sampleEnd();
        sampleResult.setSuccessful(success);
        sampleResult.setResponseCodeOK();
        sampleResult.setResponseMessageOK();

        return sampleResult;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        operation = context.getParameter("operation(create/query)");
        synchronized (TomcatJdbcTest.class) {
            logger.error("init");
            String dbPoolType = context.getParameter("poolType");
            int initialSize = 10;

            if (dbPoolType.equalsIgnoreCase("tomcat")) {
                if (tomcatDatasource != null) {
                    return;
                }

                PoolProperties p = new PoolProperties();
                p.setUrl(context.getParameter("url"));
                p.setDriverClassName("com.mysql.jdbc.Driver");
                p.setUsername(context.getParameter("username"));
                p.setPassword(context.getParameter("password"));
                p.setJmxEnabled(true);
                p.setTestWhileIdle(false);
                p.setTestOnBorrow(true);
                p.setValidationQuery("SELECT 1");
                p.setTestOnReturn(false);
                p.setValidationInterval(30000);
                p.setTimeBetweenEvictionRunsMillis(30000);
                p.setMaxActive(context.getIntParameter("maxActive"));
                p.setInitialSize(initialSize);
                p.setMaxWait(10000);
                p.setRemoveAbandonedTimeout(60);
                p.setMinEvictableIdleTimeMillis(30000);
                p.setMinIdle(10);
                p.setLogAbandoned(true);
                p.setRemoveAbandoned(true);

                tomcatDatasource = new DataSource();
                tomcatDatasource.setPoolProperties(p);
            } else {
                // dbcp
                if (dbcpDataSource != null) {
                    return;
                }

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ObjectPool connectionPool = new GenericObjectPool(
                    null,
                    context.getIntParameter("maxActive"),
                    GenericObjectPool.WHEN_EXHAUSTED_BLOCK,
                    10000,
                    context.getIntParameter("maxActive"),
                    10,
                    true,
                    false,
                    30000,
                    10/* numTestsPerEvictionRun */,
                    30000,
                    false/* testWhileIdle */,
                    30000/* softMinEvictableIdleTimeMillis */,
                    true/* lifo */);

                String url = context.getParameter("url") + "?user="
                        + context.getParameter("username") + "&password="
                        + context.getParameter("password");
                ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, null);
                new PoolableConnectionFactory(
                    connectionFactory,
                    connectionPool,
                    null,
                    null,
                    false,
                    true);
                dbcpDataSource = new PoolingDataSource(connectionPool);
            }
        }
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {

        super.teardownTest(context);
    }

}
