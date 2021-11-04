package com.sun.gobang.entity.response;

/**
 * @author sunkai
 * @since 2021/11/4 5:08 下午
 */
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
