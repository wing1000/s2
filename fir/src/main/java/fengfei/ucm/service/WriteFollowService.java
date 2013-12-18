package fengfei.ucm.service;

import fengfei.ucm.dao.UnitNames;

import java.util.ArrayList;
import java.util.List;

public interface WriteFollowService extends UnitNames {

    boolean add(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type)
            throws Exception;

    boolean add(ArrayList<Object> results, long sourceId, long targetId, byte type)
            throws Exception;

    boolean remove(ArrayList<Object> results, long sourceId, long targetId, byte type)
            throws Exception;

    boolean remove(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type)
            throws Exception;

    //with attachment
    boolean add(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type, long attachmentId)
            throws Exception;

    boolean add(ArrayList<Object> results, long sourceId, long targetId, byte type, long attachmentId)
            throws Exception;

    boolean remove(ArrayList<Object> results, long sourceId, long targetId, byte type, long attachmentId)
            throws Exception;

    boolean remove(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type, long attachmentId)
            throws Exception;

}
