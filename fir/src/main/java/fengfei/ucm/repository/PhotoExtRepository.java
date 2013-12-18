package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.photo.Refresh;

public interface PhotoExtRepository extends UnitNames {

    long updateUpcoming(final Rank rank) throws Exception;

    long deletePopular(final Rank rank) throws Exception;

    long updatePopular(final Rank rank) throws Exception;

    int addRefresh(final Refresh refresh) throws Exception;

    int deleteRefresh(final long idPhoto) throws Exception;

    int deleteRefreshByAt(final long at) throws Exception;



}
