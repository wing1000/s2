package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.ucm.dao.transducer.IntTransducer;
import fengfei.ucm.dao.transducer.MetadataTransducer;
import fengfei.ucm.dao.transducer.RelationTransducer;
import fengfei.ucm.entity.relation.Metadata;
import fengfei.ucm.entity.relation.Relation;
import fengfei.ucm.entity.relation.State;

import java.sql.SQLException;
import java.util.List;

public class RelationDao {

    private static RelationDao daoUtils = new RelationDao();

    public RelationDao() {
    }

    public static RelationDao get() {
        return daoUtils;
    }

    // get relation
    public Relation getRelation(
            ForestGrower grower,
            String suffix,
            long sourceId,
            long targetId,
            byte type) throws SQLException {
        String sql = "SELECT source_id,target_id,type,state,created_at,updated_at,attachment_id FROM rs_" + suffix
                + " WHERE source_id = ? AND target_id = ? AND type= ? ";
        Relation oldRelation = grower.selectOne(
                sql,
                new RelationTransducer(),
                sourceId,
                targetId,
                type);
        return oldRelation;
    } // get relation

    public Relation atomicallyRelation(
            ForestGrower grower,
            String suffix,
            long sourceId,
            long targetId,
            byte type) throws SQLException {
        String sql = "SELECT source_id,target_id,type,state,created_at,updated_at,attachment_id FROM rs" + suffix
                + " WHERE source_id = ? AND target_id = ? AND type= ? for update";
        Relation oldRelation = grower.selectOne(
                sql,
                new RelationTransducer(),
                sourceId,
                targetId,
                type);
        return oldRelation;
    }

    public Metadata   atomicallyMetadata(ForestGrower grower, String suffix, long sourceId, byte type)
            throws SQLException {

        Metadata metadata = grower.selectOne(
                "SELECT source_id,`count`,type,state,created_at,updated_at   FROM metadata" + suffix
                        + " WHERE source_id = ? AND type= ?  ",
                new MetadataTransducer(),
                sourceId,
                type);

        return metadata;
    }

    public int updateCount(ForestGrower grower, String suffix, long sourceId, int rows)
            throws SQLException {
        String sql = "UPDATE metadata" + suffix + " SET count = count + ?  WHERE source_id = ?";
        int updated = grower.update(sql, rows, sourceId);
        return updated;
    }

    // write relation by state
    public int write(ForestGrower grower, String suffix, Relation relation) throws SQLException {
        int count = 0;

        // step 1: lock metadata
        Metadata oldMetadata = atomicallyMetadata(
                grower,
                suffix,
                relation.getSourceId(),
                relation.getType());
        // step 2: lock relation
        Relation oldRelation = atomicallyRelation(
                grower,
                suffix,
                relation.getSourceId(),
                relation.getTargetId(),
                relation.getType());
        if (oldRelation == null) {
            count = insertRelation(grower, suffix, relation);
            if (relation.getState() == State.Removed.getCode()) {
                count = 0;
            }
        } else {
            count = updateRelation(grower, suffix, oldRelation, relation);
            if (relation.state == State.Removed.getCode()) {
                if (oldRelation.state == relation.state) {
                    count = 0;
                } else {
                    count = -count;
                }
            } else {
                if (oldRelation.state == State.Removed.getCode()) {
                } else {
                    count = 0;
                }
            }
        }
        int updated = writeMetadata(grower, suffix, oldMetadata, relation, count);
        return count;
    }

    // update relation
    public int updateRelation(
            ForestGrower grower,
            String suffix,
            Relation oldRelation,
            Relation relation) throws SQLException {
        if (oldRelation.getUpdatedAt() >= relation.getUpdatedAt())
            return 0;
        int count = 0;

        String sql = "UPDATE rs" + suffix + " SET updated_at = ?, state = ?  "
                + "WHERE source_id = ? AND type = ?  AND target_id = ? and attachment_id=? ";
        count = grower.update(
                sql,
                relation.getUpdatedAt(),
                relation.getState(),
                oldRelation.getSourceId(),
                oldRelation.getType(),
                oldRelation.getTargetId(), oldRelation.attachmentId);
        //
        return count;
    }

    // insert relation
    public int insertRelation(ForestGrower grower, String suffix, Relation relation)
            throws SQLException {
        int count = 0;
        String sql = "";

        sql = "INSERT INTO rs" + suffix
                + "(source_id, target_id,type,state,updated_at,created_at,attachment_id) "
                + "VALUES (?, ?, ?, ?, ?, ?,?)";
        count = grower.update(
                sql,
                relation.getSourceId(),
                relation.getTargetId(),
                relation.getType(),
                relation.getState(),
                relation.getUpdatedAt(),
                relation.getCreatedAt(), relation.attachmentId);

        return count;
    }

    // write metadata
    public int writeMetadata(
            ForestGrower grower,
            String suffix,
            Metadata oldMetadata,
            Relation relation,
            int rows) throws SQLException {
        int updated = 0;
        if (oldMetadata == null) {
            updated = insertMetadata(grower, suffix, relation, rows);
        } else {
            updated = updateMetadata(grower, suffix, relation, rows, oldMetadata);

        }
        return updated;
    }

    // update metadata
    public int updateMetadata(
            ForestGrower grower,
            String suffix,
            Relation relation,
            int rows,
            Metadata oldMetadata) throws SQLException {
        int count = 0;
        String sql = "UPDATE metadata" + suffix
                + " SET count = GREATEST(count + ?, 0), updated_at=?, state=? "
                + "WHERE source_id = ? AND type = ?";

        if (oldMetadata.count - count < 0) {
            count = 0;
        }
        count = grower.update(
                sql,
                rows,
                relation.getUpdatedAt(),
                oldMetadata.getState(),
                relation.getSourceId(),
                relation.getType());
        return count;
    }

    // insert metadata
    public int insertMetadata(ForestGrower grower, String suffix, Relation relation, int rows)
            throws SQLException {
        int count = 0;
        Long currentTime = System.currentTimeMillis() / 1000;
        rows = relation.getState() == State.Removed.getCode() ? 0 : rows;
        if (rows < 0) {
            rows = 0;
        }
        String sql = "INSERT INTO metadata" + suffix + " (source_id, count, type, state, "
                + "updated_at,created_at) VALUES (?, ?, ?, ?, ?, ?)";
        count = grower.update(
                sql,
                relation.getSourceId(),
                rows,
                relation.getType(),
                State.Normal.getCode(),
                relation.getUpdatedAt(),
                currentTime);
        return count;
    }

    // insert relation
    public int deleteRelation(ForestGrower grower, String suffix, Relation relation)
            throws SQLException {
        int count = 0;
        String sql = "";

        sql = "DELETE FROM rs" + suffix
                + " WHERE source_id=? AND target_id=? AND type=? AND attachment_id=?";
        count = grower.update(
                sql,
                relation.getSourceId(),
                relation.getTargetId(),
                relation.getType(),
                relation.attachmentId);

        return count;
    }

    // find
    public List<Long> find(
            ForestGrower grower,
            String suffix,
            long sourceId,
            byte type,
            State state) throws SQLException {
        List<Long> targets = grower
                .select(
                        "SELECT target_id FROM rs" + suffix
                                + " WHERE source_id =? and  type=? and  state=? ",
                        new LongTransducer(),
                        sourceId,
                        type,
                        state.getCode());
        return targets;
    }

    public List<Long> find(
            ForestGrower grower,
            String suffix,
            long sourceId,
            byte type,
            State state,
            int offset,
            int limit) throws SQLException {
        List<Long> targets = grower.select(
                "SELECT target_id FROM rs" + suffix
                        + " WHERE source_id =? and  type=? and  state=? limit ?,? ",
                new LongTransducer(),
                sourceId,
                type,
                state.getCode(),
                offset,
                limit);
        return targets;
    }

    // find
    public List<Long> findAttachments(
            ForestGrower grower,
            String suffix,
            long sourceId,
            byte type,
            State state) throws SQLException {
        List<Long> targets = grower
                .select(
                        "SELECT attachment_id FROM rs" + suffix
                                + " WHERE source_id =? and  type=? and  state=? ",
                        new LongTransducer(),
                        sourceId,
                        type,
                        state.getCode());
        return targets;
    }

    public List<Long> findAttachments(
            ForestGrower grower,
            String suffix,
            long sourceId,
            byte type,
            State state,
            int offset,
            int limit) throws SQLException {
        List<Long> targets = grower.select(
                "SELECT attachment_id FROM rs" + suffix
                        + " WHERE source_id =? and  type=? and  state=? limit ?,? ",
                new LongTransducer(),
                sourceId,
                type,
                state.getCode(),
                offset,
                limit);
        return targets;
    }

    public boolean isFollow(
            ForestGrower grower,
            String suffix,
            long sourceId,
            long targetId,
            byte type,
            State state) throws SQLException {
        Long target = grower.selectOne(
                "SELECT target_id FROM rs" + suffix
                        + " WHERE source_id =? and target_id=? and  type=? and state=? ",
                new LongTransducer(),
                sourceId,
                targetId,
                type,
                state.getCode());
        System.out.printf("%d %d %d %d \n", sourceId, targetId, type, state.getCode());
        return target != null;
    }

    public List<Long> findTargets(ForestGrower grower, String suffix, long sourceId, State state)
            throws SQLException {
        List<Long> targets = grower.select(
                "SELECT target_id FROM rs" + suffix + " WHERE source_id =? and  state=? ",
                new LongTransducer(),
                sourceId,
                state.getCode());
        return targets;
    }

    public List<Long> findTargets(
            ForestGrower grower,
            String suffix,
            long sourceId,
            State state,
            int offset,
            int limit) throws SQLException {
        List<Long> targets = grower.select(
                "SELECT target_id FROM rs" + suffix + " WHERE source_id =? and  state=? limit ?,?",
                new LongTransducer(),
                sourceId,
                state.getCode(),
                offset,
                limit);
        return targets;
    }

    public int computeRelationCount(
            ForestGrower grower,
            String suffix,
            long sourceId,
            byte type,
            State state) throws SQLException {
        String sql = "SELECT count(*) FROM rs" + suffix
                + " WHERE source_id = ? AND type=? and state = ?";
        Integer count = grower.selectOne(sql, new IntTransducer(), sourceId, type, state.getCode());
        return count == null ? 0 : count;

    }

    public int
    computeRelationCount(ForestGrower grower, String suffix, long sourceId, State state)
            throws SQLException {
        String sql = "SELECT count(*) FROM rs" + suffix + " WHERE source_id = ? and state = ?";
        Integer count = grower.selectOne(sql, new IntTransducer(), sourceId, state.getCode());
        return count == null ? 0 : count;

    }

    public int count(ForestGrower grower, String suffix, long sourceId, byte type, State state)
            throws SQLException {
        String sql = "SELECT count FROM metadata" + suffix
                + " WHERE source_id = ? AND type=? and state = ?";
        Integer count = grower.selectOne(sql, new IntTransducer(), sourceId, type, state.getCode());
        return count == null ? 0 : count;
    }

    public int count(ForestGrower grower, String suffix, long sourceId, State state)
            throws SQLException {
        String sql = "SELECT `count` as count FROM metadata" + suffix
                + " WHERE source_id = ?  and state = ?";
        Integer count = grower.selectOne(sql, new IntTransducer(), sourceId, state.getCode());
        return count == null ? 0 : count;
    }

}
