package fengfei.ucm.repository.impl;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;
import fengfei.ucm.dao.TagDao;
import fengfei.ucm.entity.photo.Tag;
import fengfei.ucm.entity.photo.Tag.TagType;
import fengfei.ucm.repository.TagRepository;

import java.sql.SQLException;
import java.util.List;

public class SqlTagRepository implements TagRepository {

    @Override
    public boolean addTags(final long idContent, final TagType type, final String... tags)
        throws Exception {

            int updated = Transactions.execute(
                PhotoUnitName,
                idContent,
                Function.Write,
                new TaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = "";
                        return TagDao.saveTags(grower, suffix, idContent, type, tags);
                    }

                });

            return updated > 0;
    }

    @Override
    public List<Tag> select(String name, String type, int offset, int limit)
        throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tag> select(String name, int offset, int limit) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
