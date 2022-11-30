import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        DatagramSocket socket = new DatagramSocket(50_000);

        while (true) {
            socket.receive(packet);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());
            log.info("Получено от клиента: " + received);

            if ("Stop".equals(received)) {
                break;
            }

            packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);
        }

        log.info("Сервер завершает свою работу.");
        socket.close();
    }
}
