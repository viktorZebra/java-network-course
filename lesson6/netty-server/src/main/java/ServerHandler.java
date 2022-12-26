import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

      @Override
      public void channelActive(ChannelHandlerContext ctx) throws UnknownHostException {
          ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\n");
      }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        String[] command = msg.split(":");

        String response;
        if (command[1].length() == Integer.parseInt(command[0])) {
            response = "2:ок";
        } else {
            response =  "3:err";
        }

        ctx.writeAndFlush("-> " + response + '\n');
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
