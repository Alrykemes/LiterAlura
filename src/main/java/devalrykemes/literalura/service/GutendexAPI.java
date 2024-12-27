package devalrykemes.literalura.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexAPI {

    private HttpClient client = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    public JsonObject RequestBooksByTitle(String title) throws IOException, InterruptedException {

        title = title.replaceAll(" ", "%20");

        String url ="https://gutendex.com/books/?search=" + title;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), JsonObject.class);
    }

    public JsonObject RequestAuthorsInIntervalOfYears(int yearIntervalOne, int yearIntervalTwo) throws IOException, InterruptedException {

        if (yearIntervalOne < 0 || yearIntervalTwo < 0) {
            throw new IllegalArgumentException("Year interval cannot be negative");
        } else if (yearIntervalOne > yearIntervalTwo) {
            throw new IllegalArgumentException("Year interval cannot be greater than year interval");
        }

        String url ="https://gutendex.com/books?author_year_start=" + yearIntervalOne + "&author_year_end=" + yearIntervalTwo + " ";

        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), JsonObject.class);
    }
}
