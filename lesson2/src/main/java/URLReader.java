import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLReader {

    private static final Logger log = LoggerFactory.getLogger(URLReader.class);

    public static void getInfo(String url) throws IOException {

        URL address = new URL(url);
        URLConnection connection = address.openConnection();

        if (connection.getContentLength() == -1) {
            log.info("Неизвестен размер запришиваемого ресурса");
            return;
        }

        log.info("Address: " + url);
        log.info("Content length: " + connection.getContentLength());
        log.info("Content type: " + connection.getContentType());

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        in.lines().forEach(log::info);

    }
}
