package com.xrtz.rongim.application;

import android.app.Application;
import android.os.Bundle;

import com.xrtz.rongim.bean.User;
import com.xrtz.rongim.bean.UserResult;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ImApplication extends Application {
    static ImApplication instance;
    public UserResult userResult;

    public UserResult getUserResult() {
        return userResult;
    }

    public void setUserResult(UserResult user) {
        this.userResult = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
        RongIM.init(this);
    }

    public static ImApplication newInstance() {
        return instance;
    }
}