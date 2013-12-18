package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.PhotoInfo;

import java.util.List;

public interface ShowRelationRepository extends UnitNames {

    List<PhotoInfo> folllowingPhoto(int idUser, int offset, int row) throws Exception;

}
