package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.relation.State;

import java.util.List;

public interface RelationRepository extends UnitNames {
    public final static long NullAttachmentId = -1;

    boolean write(long sourceId, long targetId, byte type, State state) throws Exception;

    boolean write(long sourceId, long targetId, byte type, State state, long attachmentId) throws Exception;

    List<Long> findTargets(long sourceId, byte type, State state) throws Exception;

    List<Long> findSources(long targetId, byte type, State state, int offset, int limit)
            throws Exception;

    List<Long> findTargets(long sourceId, byte type, State state, int offset, int limit)
            throws Exception;

    List<Long> findSources(long targetId, byte type, State state) throws Exception;

    int computeSourceCount(long targetId, byte type, State state) throws Exception;

    int computeTargetCount(long sourceId, byte type, State state) throws Exception;

    int countTarget(long sourceId, byte type, State state) throws Exception;

    int countSource(long targetId, byte type, State state) throws Exception;

    boolean isFollow(final long sourceId, final long targetId, byte type, final State state)
            throws Exception;

    int[] count(long sourceId, byte type) throws Exception;

    int[] computeCount(long sourceId, byte type) throws Exception;

    //
    List<Long> findTargetAttachments(long sourceId, byte type, State state) throws Exception;

    List<Long> findSourceAttachments(long targetId, byte type, State state, int offset, int limit)
            throws Exception;

    List<Long> findTargetAttachments(long sourceId, byte type, State state, int offset, int limit)
            throws Exception;

    List<Long> findSourceAttachments(long targetId, byte type, State state) throws Exception;

}
