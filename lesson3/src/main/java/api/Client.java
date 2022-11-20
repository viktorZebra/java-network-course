package api;

import api.vo.LessonRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void loginRequestAsync(String uri, String login, String password) throws URISyntaxException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        final LessonRequest request = new LessonRequest(login, password);
        String requestBody = objectMapper.writeValueAsString(request);

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .version(HttpClient.Version.HTTP_2)
                .build();

        final CompletableFuture<Void> responseFuture = client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    log.info("Response from: " + response.uri());
                    log.info("Response status code: " + response.statusCode());
                    log.info("Response body: " + response.body());
                });

        log.info("Response:\n" + responseFuture);
    }
}
