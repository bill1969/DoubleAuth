package viewstar.doubleauth.demo.entity;

public enum ResultEnum {
    UNKNOWN_ERROR(481, "未知错误"),
    USER_IS_EXIST(482, "该用户已存在"),
    USER_NOT_EXIST(483, "不存在该用户"),
    USER_IN_WHITELIST(484, "用户在白名单里"),
    USER_IN_BLACKLIST(485, "用户在黑名单里"),
    USER_NOT_IN_WHITELIST(486, "用户不在白名单里"),
    USER_NOT_IN_BLACKLIST(487, "用户不在黑名单里"),
    USER_IS_NULL(488, "用户类为空"),
    USERID_IS_NULL(489, "用户ID为空"),
    SUCCESS(200, "success"),
    SYSTEM_ERROR(500,"系统错误");

    private Integer code;

    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
