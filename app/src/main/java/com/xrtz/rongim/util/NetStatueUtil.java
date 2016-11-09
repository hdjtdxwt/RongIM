package com.xrtz.rongim.util;

/**
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 */
public class NetStatueUtil {
    /**
     * 判断网络是否连接
     * @param ctx
     * @return
     */
    public static boolean isConnected(Context ctx){
        if(ctx==null){//没有连接上
            return false;
        }
        ConnectivityManager manager =  (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info =  manager.getActiveNetworkInfo();
        if(info!=null && info.isConnected()){
            return true;
        }
        return false;
    }
}

