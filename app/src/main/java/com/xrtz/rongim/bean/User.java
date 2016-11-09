package com.xrtz.rongim.bean;

/**
 * Created by Administrator on 2016/11/4.
 */

public class User {

    /**
     *  {"status":1,"data":{"userNicheng":"abcabc","userId":13,"userPassword":"123456","userName":"abcabc","userEmail":"abcabc@126.com"},"msg":"注册成功！"}
        注册成功方法如上的内容
     * userNicheng : abcabc
     * userId : 13
     * userPassword : 123456
     * userName : abcabc
     * userEmail : abcabc@126.com
     */

    private String userNicheng;
    private int userId;
    private String userPassword;
    private String userName;
    private String userEmail;

    public String getUserNicheng() {
        return userNicheng;
    }

    public void setUserNicheng(String userNicheng) {
        this.userNicheng = userNicheng;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
