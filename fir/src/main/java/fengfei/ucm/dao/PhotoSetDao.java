package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.LongTransducer;
import fengfei.ucm.dao.transducer.IntegerTransducer;
import fengfei.ucm.dao.transducer.PhotoSetTransducer;
import fengfei.ucm.entity.photo.PhotoSet;

import java.sql.SQLException;
import java.util.List;

public class PhotoSetDao {

	final static String Insert = "insert into photo_set%s(name,id_user,create_at,update_at) values(?,?,?,?)";
	final static String Delete = "delete from photo_set%s where id_set=? and id_user=?";
	final static String Update = "update photo_set%s set name=?,update_at=? where id_set=? and id_user=?";
	final static String SelectUserSet = "select id_set,name,id_user,create_at,update_at from photo_set%s where id_user=?";
	final static String SelectPhotoSetByIDForLock = "select id_set,name,id_user,create_at,update_at from photo_set%s where id_set=? and id_user=? for update";
	final static String SelectPhotoSetByNameForLock = "select id_set,name,id_user,create_at,update_at from photo_set%s where name=? and id_user=? for update";
	final static String CountUserSet = "select count(1) from photo_set%s where id_user=?";
	//
	final static String SelectSetPhoto = "select id_photo from photo_sets%s where id_user=? and id_set=?";
	final static String InsertPhotoSets = "insert into photo_sets%s(id_set,id_photo,id_user) values(?,?,?)";
	final static String DeletePhotoSets = "delete from photo_sets%s where id_photo=? and id_user=?";
	final static String DeleteUserPhotoSets = "delete from photo_sets%s where id_set=? and id_user=?";
	//
	final static String SelectIdSetPhoto = "select id_set from photo_sets%s where id_user=? and id_photo=?";
	final static String GetPhotoSet = "select a.id_set,a.name,a.id_user,a.create_at,a.update_at from photo_set%s a join photo_sets%s b on a.id_set=b.id_set where b.id_user=? and b.id_photo=?";

	public static int save(ForestGrower grower, String suffix, PhotoSet m)
			throws SQLException {
		boolean canAdd = true;
		PhotoSet set = grower.selectOne(
				String.format(SelectPhotoSetByNameForLock, suffix),
				new PhotoSetTransducer(), m.name, m.idUser);
		if (set != null && set.name.equals(m.name) && set.idSet != m.idSet) {
			return -2;
		}
		if (m.idSet > 0) {

			if (m.idSet == set.idSet) {
				canAdd = false;
			} else {
				PhotoSet set2 = grower.selectOne(
						String.format(SelectPhotoSetByIDForLock, suffix),
						new PhotoSetTransducer(), m.idSet, m.idUser);
				if (set2 == null) {
					canAdd = true;
				} else {
					canAdd = false;
					if (set2.name.equals(m.name)) {
						return -2;
					}
				}

			}
		} else {
			canAdd = true;
		}
		if (canAdd) {

			int updated = grower.update(String.format(Insert, suffix), m.name,
					m.idUser, m.createAt, m.updateAt);
			return updated;
		} else {
			int updated = grower.update(String.format(Update, suffix), m.name,
					m.updateAt, m.idSet, m.idUser);
			return updated;
		}
	}

	public static int delete(ForestGrower grower, String suffix, long idSet,
			Integer idUser) throws SQLException {

		int deleted2 = grower.update(String.format(Delete, suffix), idSet,
				idUser);
		return deleted2;
	}

	public static int deleteUserPhotoSetsById(ForestGrower grower,
			String suffix, long idSet, Integer idUser) throws SQLException {

		int deleted1 = grower.update(
				String.format(DeleteUserPhotoSets, suffix), idSet, idUser);
		return deleted1;
	}

	public static List<PhotoSet> selectUserSets(ForestGrower grower,
			String suffix, Integer idUser) throws SQLException {
		List<PhotoSet> sets = grower.select(
				String.format(SelectUserSet, suffix), new PhotoSetTransducer(),
				idUser);
		return sets;
	}

	public static PhotoSet getPhotoSet(ForestGrower grower, String suffix,
			long idPhoto, Integer idUser) throws SQLException {
		PhotoSet photoSet = grower.selectOne(
				String.format(GetPhotoSet, suffix, suffix),
				new PhotoSetTransducer(), idUser, idPhoto);
		return photoSet;

	}

	public static long getPhotoSetId(ForestGrower grower, String suffix,
			long idPhoto, Integer idUser) throws SQLException {
		Long idSet = grower.selectOne(String.format(SelectIdSetPhoto, suffix),
				new LongTransducer(), idUser, idPhoto);
		return idSet == null ? -1 : idSet;

	}

	public static List<Long> selectSetPhoto(ForestGrower grower, String suffix,
			long idSet, Integer idUser) throws SQLException {
		List<Long> photos = grower.select(
				String.format(SelectSetPhoto, suffix), new LongTransducer(),
				idUser, idSet);
		return photos;
	}

	public static int addPhotoSets(ForestGrower grower, String suffix,
			Integer idUser, long idSet, long idPhoto) throws SQLException {
		int updated = grower.update(String.format(InsertPhotoSets, suffix),
				idSet, idPhoto, idUser);
		return updated;
	}

	public static int updatePhotoSets(ForestGrower grower, String suffix,
			long idSet, Integer idUser, long idPhoto) throws SQLException {
		int deleted = grower.update(String.format(DeletePhotoSets, suffix),
				idPhoto, idUser);
		int updated = grower.update(String.format(InsertPhotoSets, suffix),
				idSet, idPhoto, idUser);
		return updated;
	}

	public static int countUserPhotoSet(ForestGrower grower, String suffix,
			Integer idUser) throws SQLException {
		int ct = grower.selectOne(String.format(CountUserSet, suffix),
				new IntegerTransducer(), idUser);
		return ct;
	}

	public static int deletePhotoSets(ForestGrower grower, String suffix,
			Integer idUser, long idPhoto) throws SQLException {
		int updated = grower.update(String.format(DeletePhotoSets, suffix),
				idPhoto, idUser);
		return updated;
	}

}
