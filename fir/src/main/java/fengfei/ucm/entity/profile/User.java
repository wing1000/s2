package fengfei.ucm.entity.profile;

import fengfei.fir.utils.AppUtils;

import java.io.Serializable;

public class User extends UserPwd implements Serializable {

    private static final long serialVersionUID = 1L;
    public String realName;
    public String niceName;
    public String birthday;
    public Integer gender = 0;
    public String phoneNum;
    public String about;
    public String city;
    public String state;
    public String country;
    public boolean isHeadPhoto = false;
    //
    public String headPath;
    public int affection; 
    public int view;
    public int vote;
    public int favorite;
    public int comment;

    //
    public String toLocation() {
        return (city == null ? "" : city) + ", " + (state == null ? "" : state) + ", "
                + (country == null ? "" : country);
    }

    public User(
        Integer idUser,
        String userName,
        String email,
        String realName,
        String birthday,
        Integer gender,
        String phoneNum,
        String about,
        String city,
        String state,
        String country) {
        super(idUser, userName, email, null);
        this.realName = realName;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.about = about;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public User(
        Integer idUser,
        String userName,
        String email,
        String realName,
        String birthday,
        Integer gender,
        String phoneNum,
        String about,
        String city,
        String state,
        String country,
        int createAt,
        long updateAt) {
        super(idUser, userName, email, null, createAt, updateAt);
        this.realName = realName;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.about = about;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public User() {
    }

    public User(UserPwd up) {
        setUserPwd(up);

    }

    public void setUserPwd(UserPwd up) {
        this.idUser = up.idUser;
        this.email = up.email;
        this.userName = up.userName;
        this.niceName =AppUtils.toNiceName(this);
        // (this.niceName == null || "".equals(this.niceName)) ? up.userName                : this.niceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

}
