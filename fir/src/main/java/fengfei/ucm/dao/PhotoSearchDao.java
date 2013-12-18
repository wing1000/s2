package fengfei.ucm.dao;

import fengfei.fir.utils.AppUtils;
import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.forest.database.dbutils.impl.ForestRunner;
import fengfei.ucm.dao.transducer.PhotoSearchTransducer;
import fengfei.ucm.entity.photo.PhotoSearch;

import java.sql.SQLException;
import java.util.List;

public class PhotoSearchDao {

    final static String Insert = "INSERT INTO photo_search%s(keyword) VALUES (?)";
    final static String Delete = "DELETE FROM photo_search%s WHERE id = ?";
    final static String SelectExists = "SELECT id_search FROM photo_search%s WHERE keyword = ?";
    final static String Selects = "SELECT id_search FROM photo_search%s WHERE %s";
    final static String InsertRelation = "INSERT INTO photo_search_relation%s(id_search,id_target,id_user) values(?,?,?)";
    final static String DeleteRelation = "delete from photo_search_relation%s where id_target=? and id_user=?";
    final static String SearchRelation = "select id_search,id_target,id_user from photo_search_relation%s where id_search in(?)";


    public static ForestRunner.InsertResultSet<Long> saveKeyword(
            ForestGrower grower,
            String suffix,
            String keyword) throws SQLException {

        Long one = grower.selectOne(String.format(SelectExists, suffix), new LongTransducer(), keyword);
        if (one == null) {
            ForestRunner.InsertResultSet<Long> l = grower.insert(String.format(Insert, suffix), keyword);
            return l;
        } else {
            return new ForestRunner.InsertResultSet<Long>(0, one);
        }
    }

    public static int saveRelation(ForestGrower grower,
                                   String suffix,
                                   long idTarget,
                                   long idUser, long idSearch) throws SQLException {
        deleteRelation(grower, suffix, idTarget, idUser);
        int updated = grower.update(String.format(InsertRelation, suffix), idSearch, idTarget, idUser);
        return updated;

    }


    public static int deleteRelation(ForestGrower grower,
                                     String suffix,
                                     long idTarget,
                                     long idUser) throws SQLException {
        int deleted = grower.update(String.format(DeleteRelation, suffix), idTarget, idUser);
        return deleted;
    }


    public static List<Long> find(
            ForestGrower grower,
            String suffix,
            String... keywords) throws SQLException {

        List<Long> photoSearches = grower.select(String.format(Selects, AppUtils.toLikes("keyword", keywords)),
                new LongTransducer());
        return photoSearches;
    }

    public static List<PhotoSearch> find(
            ForestGrower grower,
            String suffix,
            List<Long> idSearches) throws SQLException {
        String inCause = AppUtils.toInCause(idSearches);
        List<PhotoSearch> photoSearches = grower.select(String.format(SearchRelation, suffix),
                new PhotoSearchTransducer(), inCause);
        return photoSearches;
    }


}
