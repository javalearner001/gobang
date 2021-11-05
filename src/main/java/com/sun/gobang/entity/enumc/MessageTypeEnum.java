package com.sun.gobang.entity.enumc;

/**
 * @author sunkai
 * @since 2021/11/5 5:44 下午
 */
public enum MessageTypeEnum {
    BROAD_CAST(100, "广播消息"),
    MATCH_OPPONENT(1001, "匹配对手"),
    ;

    private int code;
    private String msg;

    MessageTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
