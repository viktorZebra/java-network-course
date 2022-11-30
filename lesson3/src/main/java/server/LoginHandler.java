package server;

import api.vo.LessonRequest;
import api.vo.LessonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exception.InvalidLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements HttpHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "POST":
                handlePostRequest(exchange);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported HTTP Method: " + exchange.getRequestMethod());
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        LessonRequest request = objectMapper.readValue(exchange.getRequestBody(), LessonRequest.class);

        if ("java".equals(request.getLogin())) {

            LessonResponse response = new LessonResponse("Hello java");

            exchange.sendResponseHeaders(200, response.getMessage().length());

            try (final var responseBody = exchange.getResponseBody()) {
                responseBody.write(response.getMessage().getBytes(StandardCharsets.UTF_8));
                responseBody.flush();
            }

        } else {
            log.warn("Invalid login: " + request.getLogin());
            throw new InvalidLoginException();
        }
    }
}
