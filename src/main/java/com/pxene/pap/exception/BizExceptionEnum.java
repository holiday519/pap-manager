package com.pxene.pap.exception;

public enum BizExceptionEnum
{
    // 未知错误
    UnknowException(101, "Unknown exception"),
    
    // 请求参数不正确
    IllegalArgumentException(103, "Request parameter incorrect"),
    
    // 找不到指定资源
    ResourceNotFoundException(104, "Can't find resource"),
    
    // 资源冲突
    ResourceConflictException(105, "Resource conflict"),
    
    // 服务器异常
    ServerAbnormalException(106, "Server abnormal"),
    
    // 用户不存在
    UserNotFoundException(107, "User do not exists"),
    
    // 密码错误
    PasswordIncorrectException(108, "Password is incorrect"),
    
    // session过期
    SessionOverdueException(109, "Session has expired"),
    
    // 实体已存在
    EntityAlreadyExistsException(110, "Entity already exists"),
    
    // 实体已存在
    NotFoundException(111, "Entity can't be found"),
    
    // 状态不正确
    IllegalStateException(199, "Illegal state");
    
    
    private int code;
    private String msg;
    
    
    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }
    public String getMsg()
    {
        return msg;
    }
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    private BizExceptionEnum(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
}
