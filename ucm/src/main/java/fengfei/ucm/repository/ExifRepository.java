package fengfei.ucm.repository;

import java.util.List;

import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.ucm.dao.DataAccessException;
import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.profile.entity.ExifModel;

public interface ExifRepository extends UnitNames {
	List<InsertResultSet<Long>> save(List<ExifModel> models)
			throws DataAccessException;

	InsertResultSet<Long> saveOne(ExifModel m) throws DataAccessException;

	ExifModel selectOne(long idExif, int idUser) throws DataAccessException;
}
