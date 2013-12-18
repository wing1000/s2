package fengfei.ucm.repository.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.PoolableDatabaseResource;
import fengfei.ucm.dao.DataAccessException;
import fengfei.ucm.dao.ExifDao;
import fengfei.ucm.dao.Transactions;
import fengfei.ucm.dao.Transactions.TransactionCallback;
import fengfei.ucm.profile.entity.ExifModel;
import fengfei.ucm.repository.ExifRepository;

@Repository
public class SqlExifRepository implements ExifRepository {

    @Override
    public List<InsertResultSet<Long>> save(List<ExifModel> models) throws DataAccessException {
        List<InsertResultSet<Long>> insertResultSets = new ArrayList<>();
        for (ExifModel exifModel : models) {
            InsertResultSet<Long> u = saveOne(exifModel);
            insertResultSets.add(u);
        }

        return insertResultSets;
    }

    @Override
    public InsertResultSet<Long> saveOne(final ExifModel m) throws DataAccessException {
        TransactionCallback<InsertResultSet<Long>> callback = new TransactionCallback<InsertResultSet<Long>>() {

            @Override
            public InsertResultSet<Long> execute(
                ForestGrower grower,
                PoolableDatabaseResource resource) throws SQLException {
                String suffix = resource.getAlias();
                return ExifDao.saveOne(grower, suffix, m);
            }
        };
        return Transactions.execute(UserUnitName, m.idUser, Function.Write, callback);

    }

    @Override
    public ExifModel selectOne(final long idExif, final int idUser) throws DataAccessException {

        TransactionCallback<ExifModel> callback = new TransactionCallback<ExifModel>() {

            @Override
            public ExifModel execute(ForestGrower grower, PoolableDatabaseResource resource)
                throws SQLException {
                String suffix = resource.getAlias();
                return ExifDao.selectOne(grower, suffix, idExif);
            }
        };
        return Transactions.execute(UserUnitName, idUser, Function.Read, callback);
    }
}
