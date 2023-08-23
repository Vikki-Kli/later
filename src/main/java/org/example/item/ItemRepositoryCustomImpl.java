package org.example.item;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    @Override
    public boolean checkUrl(Item item) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(item.getUrl())).build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) return true;
            else return false;
        }
        catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
