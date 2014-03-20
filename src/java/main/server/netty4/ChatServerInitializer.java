package server.netty4;

import server.dao.TestMapper;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

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
        pipeline.addLast("handler", new ChatServerHandler(mapper));
    }
}
