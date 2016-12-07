package com.pxene.pap.constant;

public class HttpStatusCode
{
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NO_CONTENT = 204;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int CONFLICT = 409;
    public static final int TOO_MANY_REQUESTS = 429;
    public static final int INTERNAL_SERVER_ERROR = 500;
    
    public static final String STR_OK = "请求成功";
    public static final String STR_CREATED = "已创建";// 表示服务器在请求的响应中建立了新文档；应在Location头信息中给出它的URL。
    public static final String STR_ACCEPTED = "服务器已接受请求";
    public static final String STR_NO_CONTENT = "服务器已成功处理请求，但无内容要返回";
    public static final String STR_BAD_REQUEST = "请求无效";
    public static final String STR_UNAUTHORIZED = "认证失败";
    public static final String STR_FORBIDDEN = "认证成功，但是没有授权执行其指定的操作";
    public static final String STR_NOT_FOUND = "无法找到请求的资源";
    public static final String STR_CONFLICT = "冲突";
    public static final String STR_TOO_MANY_REQUESTS = "接口调用过快";
    public static final String STR_INTERNAL_SERVER_ERROR = "服务器内部错误";
    
    
}
