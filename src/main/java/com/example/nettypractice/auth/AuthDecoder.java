package com.example.nettypractice.auth;

import com.example.nettypractice.protocol.Message;
import com.example.nettypractice.protocol.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AuthDecoder extends ReplayingDecoder {

    private final AuthMessageFactory messageFactory = new AuthMessageFactory();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            byte messageTypeCode = in.readByte();
            MessageType messageType = MessageType.fromCode(messageTypeCode);

            if (messageType != MessageType.AUTH) { // Invalid Message Type
                log.warn("Received message with incorrect type for AuthDecoder: {}", messageType);
                in.skipBytes(in.readableBytes());
                ctx.close();
                return;
            }

            byte actionTypeCode = in.readByte();
            int bodyLength = in.readInt();

            ByteBuf body = in.readBytes(bodyLength);
            Message message = messageFactory.createMessage(actionTypeCode);
            message.read(body);
            out.add(message);

        } catch (Exception e) {
            log.error("[AUTH] Error decoding auth message:", e);
            ctx.close();
        }
    }
}
