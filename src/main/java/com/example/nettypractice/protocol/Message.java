package com.example.nettypractice.protocol;

import io.netty.buffer.ByteBuf;

public interface Message {

    MessageType getMessageType();

    byte getActionType();

    void write(ByteBuf out);

    void read(ByteBuf in);
}