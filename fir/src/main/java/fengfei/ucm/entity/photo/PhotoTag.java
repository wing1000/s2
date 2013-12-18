package fengfei.ucm.entity.photo;

public class PhotoTag {

    public long idTag;
    public long idPhoto;
    public String name;
    public byte category;

    public PhotoTag(long idTag, long idPhoto, String name, byte category) {
        this.idTag = idTag;
        this.idPhoto = idPhoto;
        this.name = name;
        this.category = category;
    }
}
