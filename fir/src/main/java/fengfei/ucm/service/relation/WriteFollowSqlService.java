package fengfei.ucm.service.relation;

import fengfei.ucm.entity.relation.State;
import fengfei.ucm.repository.RelationRepository;
import fengfei.ucm.service.WriteFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WriteFollowSqlService implements WriteFollowService {

    static Logger logger = LoggerFactory.getLogger(WriteFollowSqlService.class);
    private RelationRepository repository;

    public WriteFollowSqlService(RelationRepository repository) {
        super();
        this.repository = repository;

    }

    @Override
    public boolean add(ArrayList<Object> results, long sourceId, long targetId, byte type, long attachmentId)
            throws Exception {
        return repository.write(sourceId, targetId, type, State.Normal, attachmentId);
    }

    @Override
    public boolean remove(ArrayList<Object> results, long sourceId, long targetId, byte type, long attachmentId)
            throws Exception {
        return repository.write(sourceId, targetId, type, State.Removed, attachmentId);
    }

    @Override
    public boolean add(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type, long attachmentId) throws Exception {
        for (Long targetId : targetIds) {
            repository.write(sourceId, targetId, type, State.Normal, attachmentId);
        }
        return true;
    }

    @Override
    public boolean remove(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type, long attachmentId) throws Exception {
        for (Long targetId : targetIds) {
            repository.write(sourceId, targetId, type, State.Removed, attachmentId);
        }
        return true;
    }

    @Override
    public boolean add(ArrayList<Object> results, long sourceId, long targetId, byte type)
            throws Exception {
        return repository.write(sourceId, targetId, type, State.Normal);
    }

    @Override
    public boolean remove(ArrayList<Object> results, long sourceId, long targetId, byte type)
            throws Exception {
        return repository.write(sourceId, targetId, type, State.Removed);
    }

    @Override
    public boolean add(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type) throws Exception {
        for (Long targetId : targetIds) {
            repository.write(sourceId, targetId, type, State.Normal);
        }
        return true;
    }

    @Override
    public boolean remove(ArrayList<Object> results, long sourceId, List<Long> targetIds, byte type) throws Exception {
        for (Long targetId : targetIds) {
            repository.write(sourceId, targetId, type, State.Removed);
        }
        return true;
    }


}
