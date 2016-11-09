package com.xrtz.rongim.response;

/**
 *  用户返回数据格式的基类
 * Created by Administrator on 2016/11/4.
 */

public class BaseResponse<T> {
    protected int status;
    protected String msg;
    protected T data;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "status:"+status + "---msg:"+msg+"---data:" + data!=null?data.toString():"null";
    }
}