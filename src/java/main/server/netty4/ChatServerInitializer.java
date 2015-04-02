package server.netty4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import server.dao.TestMapper;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    TestMapper mapper;
    
    public ChatServerInitializer(TestMapper mapper) {
        this.mapper=mapper;
    }
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        pipeline.addLast("encoder", new ObjectEncoder());
        //增加心跳监测包,必须放在业务hander之前，否则不能调用userEventTriggered方法
        
        pipeline.addLast("ping", new IdleStateHandler(30, 0, 0));
        pipeline.addLast("handler", new ChatServerHandler(mapper));
        
    }
}
