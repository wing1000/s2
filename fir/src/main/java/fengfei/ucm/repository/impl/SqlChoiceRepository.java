package fengfei.ucm.repository.impl;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.slice.SliceResource;
import fengfei.forest.slice.database.PoolableDatabaseResource;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.ucm.dao.PhotoExtDao;
import fengfei.ucm.entity.photo.Choice;
import fengfei.ucm.repository.ChoiceRepository;

import java.sql.SQLException;

/**
 * @User: Tietang
 * @Date: 13-9-2
 * @Time: 下午9:09
 * To change this template use File | Settings | File Templates.
 */
public class SqlChoiceRepository implements ChoiceRepository {

    @Override
    public int addChoice(final Choice choice) throws Exception {
        Transactions.TransactionCallback<Integer> callback = new Transactions.TransactionCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                int up1 = PhotoExtDao.addChoice(grower, suffix, choice);
                return up1;
            }
        };

        return Transactions.execute(UserUnitName, 1l,
                SliceResource.Function.Write, callback);
    }

    @Override
    public int deleteChoice(final long idPhoto) throws Exception {
        Transactions.TransactionCallback<Integer> callback = new Transactions.TransactionCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                int up1 = PhotoExtDao.deleteChoice(grower, suffix, idPhoto);
                return up1;
            }
        };

        return Transactions.execute(UserUnitName, 1l,
                SliceResource.Function.Write, callback);
    }

    @Override
    public int deleteChoiceByAt(final int at) throws Exception {
        Transactions.TransactionCallback<Integer> callback = new Transactions.TransactionCallback<Integer>() {

            @Override
            public Integer execute(ForestGrower grower, PoolableDatabaseResource resource)
                    throws SQLException {
                String suffix = resource.getAlias();
                suffix = "";
                int up1 = PhotoExtDao.deleteChoiceByAt(grower, suffix, at);
                return up1;
            }
        };

        return Transactions.execute(UserUnitName, 1l,
                SliceResource.Function.Write, callback);
    }
}
