package fengfei.fir.mongo;

import com.mongodb.*;

import java.net.UnknownHostException;

public class AutoIncrUtils {

    public final static String AutoIncrCollName = "AutoIncrColl";
    public final static String CounterName = "counter";

    public static void init(DB db, String name) {
        DBCollection coll = db.getCollection(AutoIncrCollName);
        BasicDBObject insert = new BasicDBObject();
        insert.put("_id", name);
        insert.put(CounterName, 0);
        coll.insert(insert);

    }

    public static int next(DB db, String name) {
        DBCollection coll = db.getCollection(AutoIncrCollName);
        BasicDBObject update = new BasicDBObject("$inc", new BasicDBObject(CounterName, 1));
        DBObject o = coll.findAndModify(new BasicDBObject("_id", name), update);
        System.out.println(o);
        Object oid = o.get(CounterName);
        return (Integer) oid;

    }

    public static void main(String[] args) throws UnknownHostException, MongoException {
        Mongo mongo = new Mongo("172.17.20.21", 27017);
        DB db = mongo.getDB("photo");
        String name = "XXX";
        init(db, name);
        for (int i = 0; i < 10; i++) {
            System.out.println(next(db, name));
        }
    }
}
