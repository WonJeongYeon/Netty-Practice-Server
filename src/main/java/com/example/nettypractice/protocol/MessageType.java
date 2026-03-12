package com.example.nettypractice.protocol;

import lombok.Getter;

@Getter
public enum MessageType {
    AUTH((byte) 0x01),
    USER((byte) 0x02);

    private final byte code;

    MessageType(byte code) {
        this.code = code;
    }

    public static MessageType fromCode(byte code) {
        for (MessageType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type code: " + code);
    }
}
