package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.PhotoSet;

import java.util.List;

public interface PhotoManageRepository extends UnitNames {

    int saveSet(PhotoSet m) throws Exception;

    int deleteSet(long idSet, Integer idUser) throws Exception;

    List<PhotoSet> selectUserSets(Integer idUser) throws Exception;

    List<Long> selectSetPhoto(long idSet, Integer idUser) throws Exception;

    int addPhotoSets(Integer idUser, long idSet, long idPhoto) throws Exception;

    int deletePhotoSets(Integer idUser, long idPhoto) throws Exception;

    long getPhotoSetId(final long idPhoto, final Integer idUser) throws Exception;

    PhotoSet getPhotoSet(long idPhoto, Integer idUser) throws Exception;
}
