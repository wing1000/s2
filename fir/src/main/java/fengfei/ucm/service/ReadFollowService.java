package fengfei.ucm.service;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.relation.State;

import java.util.ArrayList;
import java.util.List;

public interface ReadFollowService extends UnitNames {

    List<Long> findTargets(ArrayList<Object> results, long sourceId, byte type) throws Exception;

    List<Long> findTargets(ArrayList<Object> results, long sourceId, byte type, int offset, int limit)
            throws Exception;

    int computeTargetCount(ArrayList<Object> results, long sourceId, byte type) throws Exception;

    int countTarget(ArrayList<Object> results, long sourceId, byte type) throws Exception;

    List<Long> findSources(ArrayList<Object> results, long targetId, byte type) throws Exception;

    List<Long> findSources(ArrayList<Object> results, long targetId, byte type, int offset, int limit)
            throws Exception;

    int computeSourceCount(ArrayList<Object> results, long targetId, byte type) throws Exception;

    int countSource(ArrayList<Object> results, long targetId, byte type) throws Exception;

    int[] count(ArrayList<Object> results, long sourceId, byte type) throws Exception;

    int[] computeCount(ArrayList<Object> results, long sourceId, byte type) throws Exception;

    boolean isFollow(ArrayList<Object> results, long sourceId, long targetId, byte type)
            throws Exception;

    //attachment
    List<Long> findTargetAttachments(long sourceId, byte type, State state) throws Exception;

    List<Long> findSourceAttachments(long targetId, byte type, State state, int offset, int limit)
            throws Exception;

    List<Long> findTargetAttachments(long sourceId, byte type, State state, int offset, int limit)
            throws Exception;

    List<Long> findSourceAttachments(long targetId, byte type, State state) throws Exception;

}
