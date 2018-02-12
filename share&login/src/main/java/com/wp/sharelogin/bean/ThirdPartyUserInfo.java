package com.wp.sharelogin.bean;

/**
 * 三方登录的用户信息
 * Created by WangPing on 2018/2/12.
 */

public class ThirdPartyUserInfo {
    private String uid;
    private String name;
    private String gender;
    private String iconUrl;

    public ThirdPartyUserInfo(String uid, String name, String gender, String iconUrl) {
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.iconUrl = iconUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
