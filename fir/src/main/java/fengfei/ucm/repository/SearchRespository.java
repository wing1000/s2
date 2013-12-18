package fengfei.ucm.repository;

import fengfei.fir.model.PhotoShow;
import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.profile.User;

import java.util.List;

public interface SearchRespository extends UnitNames {

    <T extends PhotoShow> List<T> selectPhotosByTag(
        String qstr,
        Byte category,
        int offset,
        int limit) throws Exception;

    <T extends PhotoShow> List<T> selectPhotos(String qstr, Byte category, int offset, int limit)
        throws Exception;

    List<User> selectUsers(String qstr, int offset, int limit) throws Exception;

}
