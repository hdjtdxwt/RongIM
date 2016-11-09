package com.xrtz.rongim.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/15.
 * 正则表达式的帮助类
 */
class BaseReg{
    static String ipReg = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
    static String portReg = "^[1-9]{1}[0-9]{0,3}$";
    static String nameReg = "^[0-9a-zA-Z]{4,16}$";
    static String pwdReg = "^[0-9a-zA-Z]{6,16}$";
    static String emailReg = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    static String nickNameReg = "[0-9a-zA-Z\\u4E00-\\u9FA5]{2,16}";

    /**
     * 某一个字符串是否匹配某一个表达式
     * @param reg
     * @param str
     * @return
     */
    public static boolean isMatch(String reg, String str){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return  matcher.matches();
    }


}
public class RegularUtil extends BaseReg{

    public static boolean isIP(String ip){
        return isMatch(ipReg,ip);
    }
    public static boolean isPort(String port){
        return isMatch(portReg,port);
    }

    /**
     * 用户名必须为4—16位的英文字母或数字
     * @return
     */
    public static boolean isUserName(String name){
        return  isMatch(nameReg,name);
    }

    public static boolean isPassword(String pwd){
        return isMatch(pwdReg,pwd);
    }
    public static boolean isEmail(String email){
        return isMatch(emailReg,email);
    }
    public static boolean isNickName(String nickName){
        return isMatch(nickNameReg,nickName);
    }
}
