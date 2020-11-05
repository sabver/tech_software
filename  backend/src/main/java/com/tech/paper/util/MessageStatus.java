package com.tech.paper.util;

public class MessageStatus {
    //业务成功
    public static int SUCCESS = 100;
    //业务失败
    public static int FAILURE = 200;
    //没有权限，这个显示比上述的业务失败要高
    public static int PERMISSION_DENIED = 300;
    //结果为空
    public static int EMPTY = 400;
    //参数错误
    public static int PARAM_ERROR = 500;
    //token过期
    public static int TOKEN_EXPIRED=600;
    //未登录
    public static int NOT_LOGIN = 700;
}
