import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpMethod;

import java.util.Scanner;

@Slf4j
public class Client {

    private static final String URI = "http://localhost/service";
    private final static int PORT = 8080;

    @SneakyThrows
    public static void main(String[] args) {

        var client = new HttpClient();
        client.start();

        var scanner = new Scanner(System.in);

        log.info("Для завершения работы клиента введите пустую строку.");
        while (true) {

            log.info("Введи свое имя: ");
            var name = scanner.nextLine();
            log.info("name = " + name);

            if (name.isEmpty())
                break;

            var response = client.newRequest(URI)
                    .port(PORT)
                    .method(HttpMethod.GET)
                    .param("name", name)
                    .send();

            log.info(response.getContentAsString());
        }

        log.info("Клиент завершает свою работу.");
        client.stop();

    }

}
