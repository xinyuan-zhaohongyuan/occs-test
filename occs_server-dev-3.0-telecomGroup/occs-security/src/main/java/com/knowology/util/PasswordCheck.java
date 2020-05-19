package com.knowology.util;

/**
 * 密码强度校验
 * @author xullent
 */
public class PasswordCheck  {
    //特殊符号
    private static  String regexZS = "\\w*";
    //数字、字母、大小写
    private static String regexZST = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{8,15}";
    public static boolean checkPassword(String password){
        //密码长度校验8~16位
        if(password.length() < 8 || password.length() > 16){
            return false;
        }
        if(password.matches(regexZS) || password.matches(regexZST)) {
            return false;
        }
        return true;
    }
}
