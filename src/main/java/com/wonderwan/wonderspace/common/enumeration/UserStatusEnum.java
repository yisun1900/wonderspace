package com.wonderwan.wonderspace.common.enumeration;

public enum UserStatusEnum {

    UNINITIALIZED(0),
    NORMAL(1),
    STOP(2);

    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    UserStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }
}
