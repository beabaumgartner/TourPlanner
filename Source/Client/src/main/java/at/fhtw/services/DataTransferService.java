package at.fhtw.services;

import at.fhtw.exceptions.*;
import at.fhtw.models.Tour;
import at.fhtw.utils.JsonFileHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class DataTransferService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public List<Tour> exportTours(String filename) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/export"))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 204) {
                throw new NoContentException("DataTransferService.exportTours() - no content");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("DataTransferService.exportTours() - internal server error");
            }

            if(filename != null) {
                JsonFileHandler.saveJsonStringToFile(filename, httpResponse.body());
            }

            List<Tour> tours = List.of(objectMapper.readValue(httpResponse.body(), Tour[].class));
            return tours;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("DataTransferService.exportTours() - failed to send request");
        }
    }
    public List<Tour> importTours(String filename) {
        try {
            String jsonString = JsonFileHandler.readJsonStringFromFile(filename);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/import"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 400) {
                throw new InternalServerErrorException("DataTransferService.importTours() - bad request");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("DataTransferService.importTours() - internal server error");
            }

            List<Tour> importTours = List.of(objectMapper.readValue(httpResponse.body(), Tour[].class));
            return importTours;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("DataTransferService.importTours() - failed to send request");
        }
    }
}
