package fengfei.ucm.repository.impl;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.ucm.dao.PhotoTagDao;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.PhotoTag;
import fengfei.ucm.repository.PhotoTagRepository;

import java.sql.SQLException;
import java.util.List;

/**
 */
public class SqlPhotoTagRepository implements PhotoTagRepository {
    public int[] saveTags(final long idContent, final byte category, final String[] oldTags,
                          final String[] newTags) throws Exception {

        int[] updated = Transactions.execute(
                PhotoUnitName,
                idContent,
                SliceResource.Function.Write,
                new Transactions.TaCallback<int[]>() {

                    @Override
                    public int[] execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        return PhotoTagDao.saveTags(grower, suffix, idContent, category, newTags);
                    }

                });

        return updated;
    }

    @Override
    public int[] saveTags(Photo photo, String[] oldTags, String[] newTags) throws Exception {
        return new int[0];
    }

    @Override
    public List<PhotoTag> fuzzilyFind(String name, Byte category, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<PhotoTag> preciselyFind(String name, Byte category, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<PhotoTag> find(String name, Byte category, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<PhotoTag> find(String name, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<Long> findPhotoIds(String name, Byte category, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<Long> findPhotoIds(String name, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }
}
