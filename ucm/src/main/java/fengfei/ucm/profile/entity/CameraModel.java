package fengfei.ucm.profile.entity;

import java.io.Serializable;

public class CameraModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private long idUser;
    private long idCamera;
    private String equipment;
    private String type;

    public CameraModel() {
    }

    public CameraModel(long idUser, long idCamera, String equipment, String type) {
        super();
        this.idUser = idUser;
        this.idCamera = idCamera;
        this.equipment = equipment;
        this.type = type;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdCamera() {
        return idCamera;
    }

    public void setIdCamera(long idCamera) {
        this.idCamera = idCamera;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
