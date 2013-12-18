package fengfei.ucm.service.relation;

import fengfei.ucm.entity.relation.State;
import fengfei.ucm.repository.RelationRepository;
import fengfei.ucm.service.ReadFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ReadFollowSqlService implements ReadFollowService {

    static Logger logger = LoggerFactory.getLogger(ReadFollowSqlService.class);
    private RelationRepository repository;

    public ReadFollowSqlService(RelationRepository repository) {
        super();
        this.repository = repository;

    }

    @Override
    public List<Long> findTargets(ArrayList<Object> results, long sourceId, byte type)
            throws Exception {
        return repository.findTargets(sourceId, type, State.Normal);
    }

    @Override
    public List<Long> findTargets(ArrayList<Object> results, long sourceId, byte type, int offset, int limit)
            throws Exception {
        return repository.findTargets(sourceId, type, State.Normal, offset, limit);
    }

    @Override
    public int computeTargetCount(ArrayList<Object> results, long sourceId, byte type)
            throws Exception {
        return repository.computeTargetCount(sourceId, type, State.Normal);
    }

    @Override
    public int countTarget(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        return repository.countTarget(sourceId, type, State.Normal);
    }

    @Override
    public List<Long> findSources(ArrayList<Object> results, long targetId, byte type)
            throws Exception {
        return repository.findSources(targetId, type, State.Normal);
    }

    @Override
    public List<Long> findSources(ArrayList<Object> results, long targetId, byte type, int offset, int limit)
            throws Exception {
        return repository.findSources(targetId, type, State.Normal, offset, limit);
    }

    @Override
    public int computeSourceCount(ArrayList<Object> results, long targetId, byte type)
            throws Exception {
        return repository.computeSourceCount(targetId, type, State.Normal);
    }

    @Override
    public int countSource(ArrayList<Object> results, long targetId, byte type) throws Exception {
        return repository.countSource(targetId, type, State.Normal);
    }

    @Override
    public int[] count(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        return repository.count(sourceId, type);

    }

    @Override
    public int[] computeCount(ArrayList<Object> results, long sourceId, byte type) throws Exception {
        return repository.computeCount(sourceId, type);
    }

    @Override
    public boolean isFollow(ArrayList<Object> results, long sourceId, long targetId, byte type)
            throws Exception {
        return repository.isFollow(sourceId, targetId, type, State.Normal);
    }

    @Override
    public List<Long> findTargetAttachments(long sourceId, byte type, State state) throws Exception {
        return repository.findTargetAttachments(sourceId, type, state);
    }

    @Override
    public List<Long> findSourceAttachments(long targetId, byte type, State state, int offset, int limit) throws Exception {
        return repository.findSourceAttachments(targetId, type, state, offset, limit);
    }

    @Override
    public List<Long> findTargetAttachments(long sourceId, byte type, State state, int offset, int limit) throws Exception {
        return repository.findTargetAttachments(sourceId, type, state, offset, limit);
    }

    @Override
    public List<Long> findSourceAttachments(long targetId, byte type, State state) throws Exception {
        return repository.findSourceAttachments(targetId, type, state);
    }
}
