package com.example.nettypractice.auth;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AuthDecoder extends ReplayingDecoder {

    private String type;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        try{
            byteBuf.markReaderIndex();
            STDMessage stdMessage;
            this.type = byteBuf.readCharSequence(STDConstants.HEADER_SIZE, CommonUtils.DEFAULT_DECODER.charset()).toString();
            log.info("Info Server Type : {}", type);
            byteBuf.resetReaderIndex();
            stdMessage = MessageFactory.makeProtocolMessage(type);
            stdMessage.read(byteBuf);
            list.add(stdMessage);
        } catch (Exception e){
            log.error("STD Message is null :", e);
        }
    }

}
