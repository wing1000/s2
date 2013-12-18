package fengfei.ucm.profile.entity;

import java.io.Serializable;

public class UserPwd implements Serializable {
	private static final long serialVersionUID = 1L;
	public long idUser;
	public String userName;
	public String email;
	public String password;

	public UserPwd() {
	}

	public UserPwd(String email, String userName, String password) {
		super();

		this.email = email;
		this.userName = userName;
		this.password = password;
	}

	public UserPwd(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public UserPwd(long idUser, String userName, String email, String password) {
		super();
		this.idUser = idUser;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (idUser ^ (idUser >>> 32));
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPwd other = (UserPwd) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idUser != other.idUser)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserPwd [idUser=" + idUser + ", userName=" + userName
				+ ", email=" + email + ", password=" + password + "]";
	}
}
