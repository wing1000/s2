package fengfei.ucm.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import fengfei.forest.database.pool.TomcatPoolableDataSourceFactory;
import fengfei.forest.slice.OverflowType;
import fengfei.forest.slice.Range;
import fengfei.forest.slice.Resource;
import fengfei.forest.slice.Slice;
import fengfei.forest.slice.SliceResource;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.PoolableDatabaseResource;
import fengfei.forest.slice.database.PoolableDatabaseRouter;
import fengfei.forest.slice.database.UrlMaker;
import fengfei.forest.slice.database.url.MysqlUrlMaker;
import fengfei.forest.slice.impl.AccuracyRouter;
import fengfei.forest.slice.plotter.HashPlotter;
import fengfei.ucm.dao.DataAccessException;
import fengfei.ucm.dao.LoopRoundEqualizer;
import fengfei.ucm.dao.Transactions;
import fengfei.ucm.relation.entity.State;
import fengfei.ucm.repository.impl.SqlRelaionRepository;

public class RelaionRepositoryTest {

    static RelaionRepository repository;
    static PoolableDatabaseRouter<Long> router;
    static AtomicLong ids = new AtomicLong(1);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        repository = new SqlRelaionRepository();
        Transactions.addRouter(RelaionRepository.Relation, routerSetup());
    }

    public static void main(String[] args) throws Exception {
        setUpBeforeClass();
        for (int i = 0; i < 100; i++) {
            PoolableDatabaseResource rs = router.locate(Long.valueOf(i));
            System.out.println(rs);
        }
    }

    private static PoolableDatabaseRouter<Long> routerSetup() {
        AccuracyRouter<Long> faced = new AccuracyRouter<>(new LoopRoundEqualizer(1000));
        // NavigableRouter<Long> faced = new NavigableRouter<>(new LoopRoundEqualizer(1000));
        router = new PoolableDatabaseRouter<>(
            faced,
            new MysqlUrlMaker(),
            new TomcatPoolableDataSourceFactory());
        setupGroup();
        router.setPlotter(new HashPlotter());
        router.setOverflowType(OverflowType.Exception);
        // router.followSetup();
        // clear();
        return router;
    }

    private static void setupGroup() {
        int size = 2;
        Resource resource = new Resource("s58.to:3306");
        resource.addExtraInfo(extraInfo());
        router.register(resource);

        for (int i = 0; i < size; i++) {
            String name = "s58.to:3306";
            long start = i * 100 + 1;
            long end = (i + 1) * 100;
            router.map(name, String.valueOf(i + 1), Function.ReadWrite, new Range(start, end));
            System.out.printf("i=%d start=%d end=%d\n", i, start, end);
        }
    }

    private static Map<String, String> extraInfo() {
        Map<String, String> extraInfo = new HashMap<String, String>();
        extraInfo.put("driverClass", "com.mysql.jdbc.Driver");
        extraInfo.put("username", "root");
        extraInfo.put("schema", "relation");
        extraInfo.put("password", "");
        extraInfo.put("host", "127.0.0.1");
        extraInfo.put("port", "3306");
        // pool config
        extraInfo.put("maxActive", "10");
        extraInfo.put("maxIdle", "10");
        extraInfo.put("minIdle", "1");
        extraInfo.put("initialSize", "2");
        extraInfo.put("maxWait", "30000");
        extraInfo.put("testOnBorrow", "true");
        return extraInfo;
    }

    private static void clear() {
        Set<String> aliases = new HashSet<>();
        Map<Long, Slice<Long>> slices = router.getSlices();
        Set<Entry<Long, Slice<Long>>> entries = slices.entrySet();
        for (Entry<Long, Slice<Long>> entry : entries) {
            Slice<Long> slice = entry.getValue();
            SliceResource facade = slice.get(10L, Function.Write);
            PoolableDatabaseResource resource = new PoolableDatabaseResource(facade);
            UrlMaker urlMaker = router.getUrlMaker();
            String url = resource.getURL();
            url = url == null ? urlMaker.makeUrl(resource) : url;
            DataSource ds = router.allPooledDataSources().get(url);
            String alias = resource.getAlias();
            if (!aliases.contains(alias)) {
                try {
                    String ssuffix = resource.getAlias() + "_following";
                    String smetadataTable = "metadata_" + ssuffix;
                    String srelationTable = "rs_" + ssuffix;
                    execute(
                        ds,
                        String.format("TRUNCATE %s", smetadataTable),
                        String.format("TRUNCATE %s", srelationTable));

                    String tsuffix = resource.getAlias() + "_followed";
                    String tmetadataTable = "metadata_" + tsuffix;
                    String trelationTable = "rs_" + tsuffix;
                    execute(
                        ds,
                        String.format("TRUNCATE %s", tmetadataTable),
                        String.format("TRUNCATE %s", trelationTable));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                aliases.add(alias);
            }

        }
    }

    private static void execute(DataSource ds, String... sql) throws SQLException {

        try (Connection conn = ds.getConnection(); Statement stmt = conn.createStatement();) {

            for (int i = 0; i < sql.length; i++) {
                boolean executed = stmt.execute(sql[i]);
                System.out.println(sql[i]);
            }
        }

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        clear();
    }

    @Test
    public void testWrite() {
        for (int i = 1; i <= 10; i++) {
            try {
                long id = ids.getAndIncrement();
                repository.write(id, id + 2, State.Normal);
                int count = repository.countTarget(id, State.Normal);
                Assert.assertEquals(1, count);
                List<Long> targets = repository.findTargets(id, State.Normal);
                Assert.assertNotNull(targets);
                Assert.assertEquals(1, targets.size());

            } catch (DataAccessException e) {
                e.printStackTrace();
                Assert.assertTrue(false);
            }
        }

    }

    @Test
    public void testWrite2() {
        try {
            long id = ids.getAndIncrement();
            int size = 10;
            for (int i = 1; i <= size; i++) {
                repository.write(id, id + 2 + i, State.Normal);
            }
            int count = repository.countTarget(id, State.Normal);
            Assert.assertEquals(size, count);
            List<Long> targets = repository.findTargets(id, State.Normal);
            Assert.assertNotNull(targets);
            Assert.assertEquals(size, targets.size());
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

}
