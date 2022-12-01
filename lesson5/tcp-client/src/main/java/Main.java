import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            log.warn("Не передан адрес сервера в качестве аргумента командной строки.");
            return;
        }

        var scanner = new Scanner(System.in);
        var client = SocketChannel.open(new InetSocketAddress(args[0], 9999));

        log.info("Команда Stop завершает работу клиента.");
        while (true) {
            log.info("Введи команду для отправки ее на сервер: ");
            var input = scanner.nextLine();
            var buffer = ByteBuffer.wrap(input.getBytes());

            if ("Stop".equals(input)) {
                client.write(buffer);
                buffer.clear();
                break;
            }

            client.write(buffer);
            buffer.clear();
            client.read(buffer);

            var response = new String(buffer.array()).trim();
            log.info("Получено от сервера: " + response);

            buffer.clear();
        }

        log.info("Клиент завершает свою работу.");
        scanner.close();
        client.close();
    }
}
