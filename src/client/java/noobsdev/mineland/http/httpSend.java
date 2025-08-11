package noobsdev.mineland.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class httpSend {
    public static void sendResponse(List<String> bodies) {
        HttpClient client = HttpClient.newHttpClient();

        List<CompletableFuture<HttpResponse<String>>> futures = bodies.stream()
                .map(body -> {
                    HttpRequest req = HttpRequest.newBuilder()
                            .uri(URI.create("https://6898bc8addf05523e55fb2b3.mockapi.io/api/v1/games"))
                            .header("Content-Type", "application/json")
                            .PUT(HttpRequest.BodyPublishers.ofString(body)) // заменили POST на PUT
                            .build();
                    return client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
                })
                .toList();
        futures.forEach(CompletableFuture::join);
    }
}

