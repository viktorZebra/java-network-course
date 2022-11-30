package server;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void localStart() throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.createContext("/login", new LoginHandler());
        server.start();
        log.info("server.Server started: " + server.getAddress());
    }
}
