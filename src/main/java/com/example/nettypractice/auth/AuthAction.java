package com.example.nettypractice.auth;

public enum AuthAction {
    LOGIN_REQUEST((byte) 0x01),
    LOGIN_RESPONSE((byte) 0x02);

    private final byte code;

    AuthAction(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static AuthAction fromCode(byte code) {
        for (AuthAction action : values()) {
            if (action.code == code) {
                return action;
            }
        }
        throw new IllegalArgumentException("Unknown auth action code: " + code);
    }
}
