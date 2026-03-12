package com.example.nettypractice.protocol.decoder;

import com.example.nettypractice.protocol.Frame;
import com.example.nettypractice.protocol.Header;
import com.example.nettypractice.protocol.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class HeaderDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // This decoder should be placed after a LengthFieldBasedFrameDecoder
        // that has already handled the framing.
        // The incoming 'in' ByteBuf is a complete frame (header + body).

        byte messageTypeCode = in.readByte();
        byte actionTypeCode = in.readByte();
        int bodyLength = in.readInt();

        // Basic validation
        if (in.readableBytes() != bodyLength) {
            throw new CorruptedFrameException("Body length mismatch. Expected " + bodyLength + ", but got " + in.readableBytes());
        }

        MessageType messageType = MessageType.fromCode(messageTypeCode);
        Header header = new Header(messageType, actionTypeCode, bodyLength);

        // Pass the body slice to the next handler in the pipeline.
        // readRetainedSlice avoids a copy and increases the ref count.
        ByteBuf body = in.readRetainedSlice(bodyLength);

        out.add(new Frame(header, body));
    }
}