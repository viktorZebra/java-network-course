import lombok.SneakyThrows;
import org.eclipse.jetty.server.Server;

public class JServer {

    private static final int PORT = 8080;

    @SneakyThrows
    public static void main(String[] args) {

        var server = new Server(PORT);
        server.setHandler(new Handler());

        server.start();
        server.join();
    }
}
