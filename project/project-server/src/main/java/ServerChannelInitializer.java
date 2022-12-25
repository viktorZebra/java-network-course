import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final StringDecoder DECODER = new StringDecoder();
    private final StringEncoder ENCODER = new StringEncoder();

    private final ServerHandler SERVER_HANDLER = new ServerHandler();

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        pipeline.addLast(SERVER_HANDLER);
    }
}