package fengfei.ucm.entity.photo;

import fengfei.fir.model.PhotoShow;
import fengfei.fir.utils.AppUtils;
import fengfei.sprucy.AppConstants;
import fengfei.ucm.entity.profile.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Photo extends PhotoShow implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    public static final byte Normal = 0;
    public static final byte Published = 0;
    //
    public String description;
    public byte category;
    public int adult;
    public int copyright;
    public byte license;
    public byte canPS;
    public String tags;
    public int createdAt;
    public Timestamp createdAtGmt;
    public long updatedAt;
    public byte status;
    public int commentCount;
    public byte jiff = 0;//瞬间
    //
    public String make;
    public String model;
    public String aperture;
    public String shutter;
    public String iso;
    public String lens;
    public String focus;
    public String ev;
    public String dateTimeOriginal;
    //
    public Short WhiteBalance;
    public String Software;
    public Short Flash;
    public Short ColorSpace;
    public Short MeteringMode;
    public Short ExposureProgram;
    public Short ExposureMode;
    public double GPSLatitude;
    public double GPSLongitude;
    public double GPSAltitude;
    public Byte GPSOrigin;// 0=photo or 1=google map or 2=baidu map
    //
    public String ip;
    public int iip;

    //
    public Rank rank;
    public User user;
    public List<Comment> comments = new ArrayList<>();
    public String[] tagList;
    public long idSet;

    public Photo() {
    }

    public Photo(
            int idUser,
            String title,
            String description,
            byte category,
            int adult,
            int copyright,
            String tags,
            int createdAt,
            Timestamp createdAtGmt,
            long updatedAt,
            int commentCount,
            String make,
            String model,
            String aperture,
            String shutter,
            String iso,
            String lens,
            String focus,
            String ev,
            String dateTimeOriginal) {
        super();
        this.idUser = idUser;
        this.title = title;
        this.description = description;
        this.category = category;
        this.adult = adult;
        this.copyright = copyright;
        this.tags = tags;
        this.createdAt = createdAt;
        this.createdAtGmt = createdAtGmt;
        this.updatedAt = updatedAt;
        this.commentCount = commentCount;
        this.make = make;
        this.model = model;
        this.aperture = aperture;
        this.shutter = shutter;
        this.iso = iso;
        this.lens = lens;
        this.focus = focus;
        this.ev = ev;
        this.dateTimeOriginal = dateTimeOriginal;
    }

    public Photo(
            long idPhoto,
            int idUser,
            String title,
            String description,
            byte category,
            int adult,
            int copyright,
            String tags,
            int createdAt,
            Timestamp createdAtGmt,
            long updatedAt,
            int commentCount,
            String make,
            String model,
            String aperture,
            String shutter,
            String iso,
            String lens,
            String focus,
            String ev,
            String dateTimeOriginal) {
        super();
        this.idPhoto = idPhoto;
        this.idUser = idUser;
        this.title = title;
        this.description = description;
        this.category = category;
        this.adult = adult;
        this.copyright = copyright;
        this.tags = tags;
        this.createdAt = createdAt;
        this.createdAtGmt = createdAtGmt;
        this.updatedAt = updatedAt;
        this.commentCount = commentCount;
        this.make = make;
        this.model = model;
        this.aperture = aperture;
        this.shutter = shutter;
        this.iso = iso;
        this.lens = lens;
        this.focus = focus;
        this.ev = ev;
        this.dateTimeOriginal = dateTimeOriginal;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getCopyright() {
        return copyright;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }

    public long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(long idPhoto) {
        this.idPhoto = idPhoto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getCategory() {
        return category;
    }

    public void setCategory(byte category) {
        this.category = category;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getShutter() {
        return shutter;
    }

    public void setShutter(String shutter) {
        this.shutter = shutter;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getEv() {
        return ev;
    }

    public void setEv(String ev) {
        this.ev = ev;
    }

    public String getDateTimeOriginal() {
        return dateTimeOriginal;
    }

    public void setDateTimeOriginal(String dateTimeOriginal) {
        this.dateTimeOriginal = dateTimeOriginal;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAtGmt() {
        return createdAtGmt;
    }

    public void setCreatedAtGmt(Timestamp createdAtGmt) {
        this.createdAtGmt = createdAtGmt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "PhotoModel [idPhoto=" + idPhoto + ", idUser=" + idUser + ", title=" + title
                + ", description=" + description + ", category=" + category + ", make=" + make
                + ", model=" + model + ", aperture=" + aperture + ", shutter=" + shutter
                + ", iso=" + iso + ", lens=" + lens + ", focus=" + focus + ", ev=" + ev
                + ", dateTimeOriginal=" + dateTimeOriginal + ", tags=" + tags + ", createdAt="
                + createdAt + ", createdAtGmt=" + createdAtGmt + ", updatedAt=" + updatedAt
                + ", commentCount=" + commentCount + "]";
    }

    public String exifToCSV() {
        StringBuilder sb = new StringBuilder();
        if (AppUtils.isEmpty(this.make)) sb.append(this.make).append(AppConstants.CommaSeparator);
        if (AppUtils.isEmpty(this.model)) sb.append(this.model).append(AppConstants.CommaSeparator);
        if (AppUtils.isEmpty(this.aperture)) sb.append(this.aperture).append(AppConstants.CommaSeparator);
        if (AppUtils.isEmpty(this.shutter)) sb.append(this.shutter).append(AppConstants.CommaSeparator);
        if (AppUtils.isEmpty(this.iso)) sb.append(this.iso).append(AppConstants.CommaSeparator);
        if (AppUtils.isEmpty(this.lens)) sb.append(this.lens).append(AppConstants.CommaSeparator);
        if (AppUtils.isEmpty(this.focus)) sb.append(this.focus).append(AppConstants.CommaSeparator);
        if (AppUtils.isEmpty(this.ev)) sb.append(this.ev).append(AppConstants.CommaSeparator);
        return sb.toString();
    }

}
