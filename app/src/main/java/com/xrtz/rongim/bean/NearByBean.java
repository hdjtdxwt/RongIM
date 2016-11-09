package com.xrtz.rongim.bean;

/**
 * Created by Administrator on 2016/11/3.
 */

public class NearByBean implements java.io.Serializable {
    private int level;
    //private String url;
    private int resId;

    private String distance;

    public NearByBean(int level, int resId, String distance) {
        this.level = level;
        this.resId = resId;
        this.distance = distance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
