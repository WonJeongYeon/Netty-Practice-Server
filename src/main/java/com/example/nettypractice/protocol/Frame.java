package com.example.nettypractice.protocol;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Frame {
    private final Header header;
    private final ByteBuf body;
}
