import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final StringDecoder DECODER = new StringDecoder();
    private final StringEncoder ENCODER = new StringEncoder();

    private final ClientHandler CLIENT_HANDLER = new ClientHandler();

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        pipeline.addLast(CLIENT_HANDLER);
    }

}