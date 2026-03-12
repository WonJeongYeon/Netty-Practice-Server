package com.example.nettypractice.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Header {
    private final MessageType messageType;
    private final byte actionType;
    private final int bodyLength;
}
