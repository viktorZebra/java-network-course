import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {

        var selector = Selector.open();
        var serverSocket = ServerSocketChannel.open();

        serverSocket.bind(new InetSocketAddress("localhost", 9999));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        var buffer = ByteBuffer.allocate(256);

        var flag = true;
        while (flag) {
            selector.select();
            var selectedKeys = selector.selectedKeys();

            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext() && flag) {

                var key = iter.next();

                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                } else if (key.isReadable()) {
                    flag = answer(buffer, key);
                }

                iter.remove();
            }
        }

        log.info("Сервер завершает свою работу.");
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        var client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private static boolean answer(ByteBuffer buffer, SelectionKey key) throws IOException {
        var client = (SocketChannel) key.channel();
        client.read(buffer);
        buffer.flip();

        var answer = new String(buffer.array(), 0, buffer.limit());
        log.info("Получено от клиента: " + answer);
        if ("Stop".equals(answer)) {
            return false;
        }

        client.write(buffer);
        buffer.clear();
        return true;
    }

}
