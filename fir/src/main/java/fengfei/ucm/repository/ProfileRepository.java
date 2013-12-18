package fengfei.ucm.repository;

import com.google.common.collect.ListMultimap;
import fengfei.fir.model.Notify;
import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.profile.Camera;
import fengfei.ucm.entity.profile.UserSocial;

import java.util.List;

public interface ProfileRepository extends UnitNames {

    boolean addCamera(List<Camera> models, int idUser) throws Exception;

    boolean addOneCamera(Camera m) throws Exception;

    boolean deleteOneCamera(final Integer idUser, final long idCamera) throws Exception;

    List<Camera> selectCameras(int idUser) throws Exception;

    List<Camera> selectCamerasForSorted(int idUser) throws Exception;

    ListMultimap<String, Camera> selectCamerasGroup(int idUser) throws Exception;

    boolean saveNotifies(
            Integer idUser,
            List<Notify> notifies) throws Exception;

    long getNotifyValue(Integer idUser) throws Exception;

    byte getDefaultLicense(Integer idUser) throws Exception;

    boolean saveDefaultLicense(Integer idUser, byte license) throws Exception;

    int saveUserSocial(UserSocial userSocial) throws Exception;

    UserSocial getUserSocial(Integer idUser) throws Exception;
}
