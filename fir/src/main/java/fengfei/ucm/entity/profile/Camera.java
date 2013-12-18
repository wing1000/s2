package fengfei.ucm.entity.profile;

import java.io.Serializable;

public class Camera implements Serializable {

    public final static String TypeCamera = "camera";
    public final static String TypeLens = "lens";
    public final static String TypeTripod = "tripod";
    public final static String TypeFilter = "filter";

    private static final long serialVersionUID = 1L;
    public int idUser;
    public long idCamera;
    public String equipment;
    public String type;

    public Camera() {
    }

    public Camera(int idUser, long idCamera, String equipment, String type) {
        super();
        this.idUser = idUser;
        this.idCamera = idCamera;
        this.equipment = equipment;
        this.type = type;
    }

    public Camera(int idUser, String equipment, String type) {
        super();
        this.idUser = idUser;
        this.equipment = equipment;
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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
