package fengfei.ucm.profile.entity;

import java.io.Serializable;

/**
 * UserAttribute
 */
public class UserAttribute implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idAttr;
	private Long idUser;
	private String value;

	public UserAttribute() {
	}

	public UserAttribute(Integer idAttr, Long idUser, String value) {
		super();
		this.idAttr = idAttr;
		this.idUser = idUser;
		this.value = value;
	}

	public Integer getIdAttr() {
		return idAttr;
	}

	public void setIdAttr(Integer idAttr) {
		this.idAttr = idAttr;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int intValue() {
		return Integer.parseInt(value);
	}

	public long longValue() {
		return Long.parseLong(value);
	}

	public float floatValue() {
		return Float.parseFloat(value);
	}

	public double doubleValue() {
		return Double.parseDouble(value);
	}

}