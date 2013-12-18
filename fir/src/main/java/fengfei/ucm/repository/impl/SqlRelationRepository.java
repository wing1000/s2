package fengfei.ucm.repository.impl;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.impl.DefaultForestGrower;
import fengfei.forest.slice.SliceResource.Function;
import fengfei.forest.slice.database.PoolableDatabaseResource;
import fengfei.forest.slice.database.utils.MutiTransaction;
import fengfei.forest.slice.database.utils.MutiTransaction.MutiTaCallback;
import fengfei.forest.slice.database.utils.MutiTransaction.TransactionModel;
import fengfei.forest.slice.database.utils.Transactions;
import fengfei.forest.slice.database.utils.Transactions.TaCallback;
import fengfei.ucm.dao.RelationDao;
import fengfei.ucm.entity.relation.Relation;
import fengfei.ucm.entity.relation.State;
import fengfei.ucm.repository.RelationRepository;

import java.sql.SQLException;
import java.util.List;

public class SqlRelationRepository implements RelationRepository {

    final RelationDao dao = RelationDao.get();

    @Override
    public boolean write(long sourceId, long targetId, final byte type, State state, long attachmentId) throws Exception {
        long ctime = System.currentTimeMillis();
        long updatedAt = ctime;
        int createdAt = (int) (ctime / 1000);
        final Relation relation = new Relation(
                sourceId,
                targetId,
                type,
                state.getCode(),
                updatedAt,
                createdAt);


        TransactionModel<Integer> sModel = getTransactionModel(
                relation.getSourceId(),
                true,
                new MutiTaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower) throws SQLException {
                        Relation r = index == 1 ? switchRelation(relation) : relation;
                        return dao.write(grower, suffix, r);
                    }
                });
        TransactionModel<Integer> tModel = getTransactionModel(
                relation.getTargetId(),
                false,
                new MutiTaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower) throws SQLException {
                        Relation r = index == 1 ? switchRelation(relation) : relation;
                        return dao.write(grower, suffix, r);
                    }
                });

        MutiTransaction<Integer> transaction = new MutiTransaction<>();
        transaction.addTransactionModel(sModel);
        transaction.addTransactionModel(tModel);
        transaction.execute();
        return true;
    }

    @Override
    public boolean write(long sourceId, long targetId, final byte type, State state) throws Exception {

        return write(sourceId, targetId, type, state, NullAttachmentId);
    }

    private <T> TransactionModel<T> getTransactionModel(
            long id,
            boolean isFoloowing,
            MutiTaCallback<T> callback) throws Exception {
        PoolableDatabaseResource resource = Transactions.get(Relation, id, Function.Write);
        return getTransactionModel(isFoloowing, resource, callback);
    }

    private <T> TransactionModel<T> getTransactionModel(
            boolean isFoloowing,
            PoolableDatabaseResource resource,
            MutiTaCallback<T> callback) throws SQLException {
        ForestGrower grower = new DefaultForestGrower(resource.getConnection());
        String alias = resource.getAlias();
        TransactionModel<T> model = new TransactionModel<>(grower, getFullSuffix(
                alias,
                isFoloowing), callback);
        return model;
    }

    protected String getFullSuffix(String alias, boolean isFoloowing) {
        return alias + (isFoloowing ? "_following" : "_followed");
    }

    @Override
    public List<Long> findTargets(final long sourceId, final byte type, final State state)
            throws Exception {

        List<Long> targets = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        return dao.find(grower, suffix, sourceId, type, state);
                    }

                });
        return targets;
    }

    @Override
    public List<Long> findTargets(
            final long sourceId, final byte type,
            final State state,
            final int offset,
            final int limit) throws Exception {

        List<Long> targets = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        return dao.find(grower, suffix, sourceId, type, state, offset, limit);
                    }

                });
        return targets;
    }

    @Override
    public int computeTargetCount(final long sourceId, final byte type, final State state)
            throws Exception {

        Integer count = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        return dao.computeRelationCount(grower, suffix, sourceId, type, state);
                    }

                });
        return count;
    }

    @Override
    public int countTarget(final long sourceId, final byte type, final State state) throws Exception {
        Integer count = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        return dao.count(grower, suffix, sourceId, type, state);
                    }

                });
        return count;
    }

    private Relation switchRelation(Relation relation) {

        Relation relationb = relation.deepCopy();
        relationb.setSourceId(relation.getTargetId());
        relationb.setTargetId(relation.getSourceId());

        return relationb;

    }

    @Override
    public List<Long> findSources(final long targetId, final byte type, final State state)
            throws Exception {

        List<Long> sources = Transactions.execute(
                Relation,
                targetId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, false);
                        return dao.find(grower, suffix, targetId, type, state);
                    }

                });
        return sources;
    }

    @Override
    public List<Long> findSources(
            final long targetId, final byte type,
            final State state,
            final int offset,
            final int limit) throws Exception {

        List<Long> sources = Transactions.execute(
                Relation,
                targetId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, false);
                        return dao.find(grower, suffix, targetId, type, state, offset, limit);
                    }

                });
        return sources;
    }

    @Override
    public int computeSourceCount(final long targetId, final byte type, final State state)
            throws Exception {

        Integer count = Transactions.execute(
                Relation,
                targetId,
                Function.Read,
                new TaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = getFullSuffix(suffix, false);
                        return dao.computeRelationCount(grower, suffix, targetId, type, state);
                    }

                });
        return count;
    }

    @Override
    public int countSource(final long targetId, final byte type, final State state) throws Exception {

        Integer count = Transactions.execute(
                Relation,
                targetId,
                Function.Read,
                new TaCallback<Integer>() {

                    @Override
                    public Integer execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = getFullSuffix(suffix, false);
                        return dao.count(grower, suffix, targetId, type, state);
                    }

                });
        return count;
    }

    @Override
    public boolean isFollow(final long sourceId, final long targetId, final byte type, final State state)
            throws Exception {

        Boolean isFollow = Transactions.execute(
                Relation,
                targetId,
                Function.Read,
                new TaCallback<Boolean>() {

                    @Override
                    public Boolean execute(ForestGrower grower, String suffix) throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        return dao.isFollow(grower, suffix, sourceId, targetId, type, state);
                    }

                });
        return isFollow;
    }

    @Override
    public int[] count(final long sourceId, final byte type) throws Exception {

        Integer[] follow = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<Integer[]>() {

                    @Override
                    public Integer[] execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        int following = dao.count(grower, suffix, sourceId, State.Normal);
                        suffix = getFullSuffix(suffix, false);
                        int followed = dao.count(grower, suffix, sourceId, type, State.Normal);
                        return new Integer[]{following, followed};
                    }

                });

        return new int[]{follow[0], follow[1]};

    }

    @Override
    public int[] computeCount(final long sourceId, final byte type) throws Exception {

        Integer[] follow = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<Integer[]>() {

                    @Override
                    public Integer[] execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        int following = dao.computeRelationCount(
                                grower,
                                suffix,
                                sourceId, type,
                                State.Normal);
                        suffix = getFullSuffix(suffix, false);
                        int followed = dao.computeRelationCount(
                                grower,
                                suffix,
                                sourceId, type,
                                State.Normal);
                        return new Integer[]{following, followed};
                    }

                });

        return new int[]{follow[0], follow[1]};

    }

    @Override
    public List<Long> findTargetAttachments(final long sourceId, final byte type, final State state) throws Exception {
        List<Long> targets = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        return dao.findAttachments(grower, suffix, sourceId, type, state);
                    }

                });
        return targets;
    }

    @Override
    public List<Long> findTargetAttachments(final long sourceId, final byte type, final State state, final int offset, final int limit) throws Exception {
        List<Long> targets = Transactions.execute(
                Relation,
                sourceId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, true);
                        return dao.findAttachments(grower, suffix, sourceId, type, state, offset, limit);
                    }

                });
        return targets;
    }

    @Override
    public List<Long> findSourceAttachments(final long targetId, final byte type, final State state, final int offset, final int limit) throws Exception {
        List<Long> sources = Transactions.execute(
                Relation,
                targetId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, false);
                        return dao.findAttachments(grower, suffix, targetId, type, state, offset, limit);
                    }

                });
        return sources;
    }


    @Override
    public List<Long> findSourceAttachments(final long targetId, final byte type, final State state) throws Exception {
        List<Long> sources = Transactions.execute(
                Relation,
                targetId,
                Function.Read,
                new TaCallback<List<Long>>() {

                    @Override
                    public List<Long> execute(ForestGrower grower, String suffix)
                            throws SQLException {
                        suffix = getFullSuffix(suffix, false);
                        return dao.findAttachments(grower, suffix, targetId, type, state);
                    }

                });
        return sources;
    }
}
