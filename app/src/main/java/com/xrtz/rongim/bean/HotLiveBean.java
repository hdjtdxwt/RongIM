package com.xrtz.rongim.bean;

/**
 * Created by Administrator on 2016/11/3.
 */

public class HotLiveBean implements java.io.Serializable {
    private String name;
    private String address;
    private String lookingNum;
    //private String url;

    private int resId;
    public HotLiveBean(String name, String address, String lookingNum, int resId) {
        this.name = name;
        this.address = address;
        this.lookingNum = lookingNum;
        this.resId = resId;
    }

    public HotLiveBean(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLookingNum() {
        return lookingNum;
    }

    public void setLookingNum(String lookingNum) {
        this.lookingNum = lookingNum;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
/*public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }*/
}
