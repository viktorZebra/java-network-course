import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer());

            ChannelFuture f = b.connect(HOST, PORT).sync();

            ChannelFuture lastWriteFuture = null;
            while (true) {
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    break;
                }

                Channel channel = f.sync().channel();
                lastWriteFuture = channel.writeAndFlush(input);
            }

            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
