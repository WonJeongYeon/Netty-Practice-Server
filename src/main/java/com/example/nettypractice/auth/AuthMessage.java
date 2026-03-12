package com.example.nettypractice.auth;

import com.example.nettypractice.protocol.Message;
import com.example.nettypractice.protocol.MessageType;
import io.netty.buffer.ByteBuf;

public abstract class AuthMessage implements Message {

    @Override
    public MessageType getMessageType() {
        return MessageType.AUTH;
    }

    public abstract AuthAction getAction();

    @Override
    public byte getActionType() {
        return getAction().getCode();
    }
}