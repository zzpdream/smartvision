package com.mws.web.net.mina;

import com.mws.core.mapper.JsonMapper;
import com.mws.web.net.bo.Constant;
import com.mws.web.net.dto.MessageDto;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class DataProtocolEncoder implements ProtocolEncoder {

    private static Logger logger = LoggerFactory.getLogger(DataProtocolEncoder.class);

    IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
    Charset charset = Charset.forName("UTF-8");

    @Override
    public void dispose(IoSession session) throws Exception {

    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput output)
            throws Exception {
        String msg;
        if (message instanceof MessageDto.Request || message instanceof MessageDto.Response) {
            msg = JsonMapper.nonDefaultMapper().toJson(message);
        } else {
            msg = message.toString();
        }
        buf.putInt(Constant.magicWord);
        buf.putInt(msg.toString().getBytes(charset.name()).length);
        buf.put(msg.toString().getBytes(charset.name()));
        buf.flip();
        output.write(buf);
        buf.clear();
    }

    public static void main(String[] args) throws Exception {
        String str = "helloWorld";
        ByteBuffer buff = ByteBuffer.wrap(str.getBytes());
        System.out.println("position:" + buff.position() + "\t limit:" + buff.limit());
        //读取两个字节
        byte b1 = buff.get();
        System.out.println((char) b1);
        byte b2 = buff.get();
        System.out.println((char) b2);
        System.out.println("position:" + (char) buff.get(buff.position()) + "\t limit:" + buff.limit());
        buff.mark();
        System.out.println("position:" + buff.position() + "\t limit:" + buff.limit());
        buff.flip();
        System.out.println("position:" + buff.position() + "\t limit:" + buff.limit());
    }

}