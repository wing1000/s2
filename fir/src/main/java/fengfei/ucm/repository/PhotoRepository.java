package fengfei.ucm.repository;

import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.Rank;

import java.util.List;

public interface PhotoRepository extends UnitNames {

    List<InsertResultSet<Long>> save(List<Photo> models, final String userName)
        throws Exception;

    InsertResultSet<Long> saveOne(Photo m, final String userName) throws Exception;

    boolean deleteOne(final long idPhoto, final Integer idUser) throws Exception;

    // InsertResultSet<Long> addOne(final Photo m, final String userName) throws
    // DataAccessException;

    int updateOne(final Photo m, final String userName) throws Exception;

    Photo selectOne(long idPhoto, Integer idUser) throws Exception;

    public Photo selectOne(final long idPhoto) throws Exception;

    public Rank getRank(final long idPhoto) throws Exception;

    //
    public Photo view(
        final long idPhoto,
        final Integer photoIdUser,
        final Integer accessIdUser,
        final int iip) throws Exception;

    int vote(
        final long idPhoto,
        final Integer photoIdUser,
        final String photoNiceName,
        final byte photoCategory,
        final Integer accessIdUser,
        final int iip) throws Exception;

    int favorite(
        final long idPhoto,
        final Integer photoIdUser,
        final String photoNiceName,
        final byte photoCategory,
        final Integer accessIdUser,
        final int iip) throws Exception;

    int unfavorite(long idPhoto, final Integer photoIdUser, Integer accessIdUser, final int iip)
        throws Exception;

    boolean isFavorite(long idPhoto, Integer accessIdUser, int iip) throws Exception;

    boolean isVote(long idPhoto, Integer accessIdUser, int iip) throws Exception;
}
