package fengfei.ucm.service.relation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.ucm.dao.DataAccessException;
import fengfei.ucm.relation.entity.State;
import fengfei.ucm.repository.RelaionRepository;
import fengfei.ucm.service.FollowService;

public class FollowSqlService implements FollowService {

    static Logger logger = LoggerFactory.getLogger(FollowSqlService.class);
    private RelaionRepository repository;

    public FollowSqlService(RelaionRepository repository) {
        super();
        this.repository = repository;

    }

    @Override
    public boolean add(ArrayList<Object> results, long sourceId, long targetId)
        throws DataAccessException {
        return repository.write(sourceId, targetId, State.Normal);
    }

    @Override
    public boolean remove(ArrayList<Object> results, long sourceId, long targetId)
        throws DataAccessException {
        return repository.write(sourceId, targetId, State.Removed);
    }

    @Override
    public List<Long> findTargets(ArrayList<Object> results, long sourceId)
        throws DataAccessException {
        return repository.findTargets(sourceId, State.Normal);
    }

    @Override
    public int computeTargetCount(ArrayList<Object> results, long sourceId)
        throws DataAccessException {
        return repository.computeTargetCount(sourceId, State.Normal);
    }

    @Override
    public int countTarget(ArrayList<Object> results, long sourceId) throws DataAccessException {
        return repository.countTarget(sourceId, State.Normal);
    }

    @Override
    public List<Long> findSources(ArrayList<Object> results, long targetId)
        throws DataAccessException {
        return repository.findSources(targetId, State.Normal);
    }

    @Override
    public int computeSourceCount(ArrayList<Object> results, long targetId)
        throws DataAccessException {
        return repository.computeSourceCount(targetId, State.Normal);
    }

    @Override
    public int countSource(ArrayList<Object> results, long targetId) throws DataAccessException {
        return repository.countSource(targetId, State.Normal);
    }

}
