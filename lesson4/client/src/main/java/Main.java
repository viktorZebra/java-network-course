import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            log.warn("Не передан адрес сервера в качестве аргумента командной строки.");
            return;
        }


        final DatagramSocket socket = new DatagramSocket();
        final InetAddress address = InetAddress.getByName(args[0]);
        final Scanner scanner = new Scanner(System.in);

        log.info("Команда Stop завершает работу клиента.");
        while (true) {
            log.info("Введи команду для отправки ее на сервер: ");
            final String input = scanner.nextLine();
            final byte[] buffer = input.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 50_000);

            if ("Stop".equals(input)) {
                socket.send(packet);
                break;
            }

            socket.send(packet);
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            log.info("Получено от сервера: " + received);
        }

        scanner.close();
        log.info("Клиент завершает свою работу.");
        socket.close();
    }
}
