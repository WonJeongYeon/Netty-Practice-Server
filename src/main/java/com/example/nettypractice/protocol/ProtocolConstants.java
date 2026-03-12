package com.example.nettypractice.protocol;

public final class ProtocolConstants {

    // 인스턴스화 방지
    private ProtocolConstants() {}

    // 헤더의 각 필드 바이트 크기 정의
    public static final int MESSAGE_TYPE_BYTES = 1;
    public static final int ACTION_TYPE_BYTES = 1;
    public static final int BODY_LENGTH_BYTES = 4;

    // 전체 헤더의 길이
    public static final int HEADER_LENGTH = MESSAGE_TYPE_BYTES + ACTION_TYPE_BYTES + BODY_LENGTH_BYTES;
}
