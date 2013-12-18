package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.Choice;
import fengfei.ucm.entity.photo.Rank;
import fengfei.ucm.entity.photo.Refresh;

import java.util.List;

public interface PhotoShowRepository extends UnitNames {

    public final static long RefreshKey = 1l;

    List<Refresh> refreshed(final Byte category, int offset, int limit) throws Exception;

    List<Choice> choiced(final Byte category, final int offset, final int limit)
        throws Exception;

    List<Rank> upcomed(final Byte category, final int offset, final int limit)
        throws Exception;

    List<Rank> popular(final Byte category, final int offset, final int limit)
        throws Exception;

}
