package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.*;

import java.util.List;

public interface ShowRepository extends UnitNames {

    public final static long RefreshKey = 1l;

    public List<Rank> selectUserPhotos(final Integer idUser, final int offset, final int limit)
        throws Exception;

    public Rank getUserRank(final Integer idUser) throws Exception;

    List<Refresh> refreshed(final Byte category, int offset, int limit) throws Exception;

    List<Choice> choiced(final Byte category, final int offset, final int limit)
        throws Exception;

    List<Rank> upcomed(final Byte category, final int offset, final int limit)
        throws Exception;

    List<Rank> popular(final Byte category, final int offset, final int limit)
        throws Exception;

    List<Refresh> categorized(final Byte category, final int offset, final int limit)
        throws Exception;

    List<Rank> selectRanks(final List<Long> idPhotos) throws Exception;

    List<Rank> pop(final int offset, final int limit) throws Exception;

    Photo get(final long idPhoto, final Integer idUser) throws Exception;

    //

    List<Favorite> selectFavorites(Integer idUser, int offset, int limit)
        throws Exception;

    List<Favorite> selectFavorites(String username, int offset, int limit)
        throws Exception;

    List<Favorite> selectFavoritesByIP(final int iip, final int offset, final int limit)
        throws Exception;

    List<PhotoAccess> selectPhotoAccesses(Integer idUser, int offset, int limit)
        throws Exception;

    List<PhotoAccess> selectPhotoAccesses(String username, int offset, int limit)
        throws Exception;

    List<PhotoAccess> selectPhotoViewsByUserId(
        final Integer idUser,
        final int offset,
        final int limit) throws Exception;

    List<PhotoAccess> selectPhotoViewsByIP(final int iip, final int offset, final int limit)
        throws Exception;
    // List<PhotoAccess> selectPhotoAccesses( int offset, int limit)
    // throws Exception;

}
