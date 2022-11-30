import api.Client;
import server.Server;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Server.localStart();
        Client.loginRequestAsync("http://localhost:8080/login", "java", "1234");
    }
}
