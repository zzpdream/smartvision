package com.mws.web.net;

import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.google.common.collect.Maps;
import com.mws.web.net.bo.Command;
import com.mws.web.net.dto.MessageDto;
import com.mws.web.net.mina.DataCodecFactory;
import com.mws.web.net.mina.TerminalClientHandler;

/**
 * Created by ranfi on 2/22/16.
 */
public class ClientTest {

    public static MessageDto.Request getRequestMessage() {

        MessageDto.Request request = new MessageDto.Request();
        request.setType("req");
        request.setCmd(Command.SET_SEAT.value);

        Map<String, Object> param = Maps.newHashMap();
        param.put("seatId", 1);
        param.put("personId", 1);
        param.put("personName", "jack");
        param.put("vote", 1);
        param.put("picZipMd5","4117e04eaf192c40708c40921e75980f");
        param.put("cardId1", "123456789");
        request.setParams(param);
        return request;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 创建客户端连接器.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        DataCodecFactory factory = new DataCodecFactory();
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(factory)); //设置编码过滤器
        connector.setHandler(new TerminalClientHandler());//设置事件处理器
        ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 5678));//建立连接
        cf.awaitUninterruptibly();//等待连接创建完成
       // cf.getSession().write(getRequestMessage());//发送消息
        cf.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开
        connector.dispose();

    }
}
