package fengfei.ucm.profile.entity;

import java.io.Serializable;

public class ExifModel implements Serializable {
	private static final long serialVersionUID = 1L;
	public long idExif;
	public int idUser;
	public String title;
	public String description;
	public String category;
	public String make;
	public String model;
	public String aperture;
	public String shutter;
	public String iso;
	public String lens;
	public String focus;
	public String ev;
	public String dateTimeOriginal;
	public String tags;

	public ExifModel(long idExif, int idUser, String title, String description,
			String category, String make, String model, String aperture,
			String shutter, String iso, String lens, String focus, String ev,
			String dateTimeOriginal, String tags) {
		super();
		this.idExif = idExif;
		this.title = title;
		this.description = description;
		this.category = category;
		this.make = make;
		this.model = model;
		this.aperture = aperture;
		this.shutter = shutter;
		this.iso = iso;
		this.lens = lens;
		this.focus = focus;
		this.ev = ev;
		this.dateTimeOriginal = dateTimeOriginal;
		this.tags = tags;
		this.idUser = idUser;
	}

	public ExifModel() {
	}

 

	public long getIdExif() {
		return idExif;
	}

	public void setIdExif(long idExif) {
		this.idExif = idExif;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ExifModel [idExif=" + idExif + ", idUser=" + idUser
				+ ", title=" + title + ", description=" + description
				+ ", category=" + category + ", make=" + make + ", model="
				+ model + ", aperture=" + aperture + ", shutter=" + shutter
				+ ", iso=" + iso + ", lens=" + lens + ", focus=" + focus
				+ ", ev=" + ev + ", dateTimeOriginal=" + dateTimeOriginal
				+ ", tags=" + tags + "]";
	}

}
