package at.fhtw.services;

import at.fhtw.exceptions.*;
import at.fhtw.models.Tour;
import at.fhtw.models.TourListEntry;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TourService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public Tour createTour(Tour tour) {
        try {
            String jsonString = objectMapper.writeValueAsString(tour);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 400) {
                throw new BadRequestException("TourService.createTour() - bad request");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourService.createTour() - internal server error");
            }

            Tour newtour = objectMapper.readValue(httpResponse.body(), Tour.class);
            return newtour;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourService.createTour() - failed to send request");
        }
    }
    public Tour getTour(Integer tourId) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours/" + tourId))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourService.getTour() - tourId not found: " + tourId);
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourService.getTour() - internal server error");
            }

            Tour newtour = objectMapper.readValue(httpResponse.body(), Tour.class);
            return newtour;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourService.getTour() - failed to send request");
        }
    }
    public List<TourListEntry> getTourList() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours"))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 204) {
                throw new NoContentException("TourService.getTourList() - no content");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourService.getTourList() - internal server error");
            }

            List<TourListEntry> tourList = List.of(objectMapper.readValue(httpResponse.body(), TourListEntry[].class));
            return tourList;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourService.getTourList() - failed to send request");
        }
    }
    public List<TourListEntry> searchTour(String keyword) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours/search/" + keyword))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 204) {
                throw new NoContentException("TourService.searchTour() - no content");
            }
            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourService.searchTour() - keyword not found: " + keyword);
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourService.searchTour() - internal server error");
            }

            List<TourListEntry> tourList = List.of(objectMapper.readValue(httpResponse.body(), TourListEntry[].class));
            return tourList;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourService.searchTour() - failed to send request");
        }
    }
    public Tour updateTour(Integer tourId, Tour tour) {
        try {
            String jsonString = objectMapper.writeValueAsString(tour);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours/" + tourId))
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 400) {
                throw new BadRequestException("TourService.updateTour() - bad request");
            }
            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourService.updateTour() - tourId not found: " + tourId);
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourService.updateTour() - internal server error");
            }

            Tour newtour = objectMapper.readValue(httpResponse.body(), Tour.class);
            return newtour;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourService.updateTour() - failed to send request");
        }
    }
    public void deleteTour(Integer tourId) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours/" + tourId))
                    .DELETE()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourService.deleteTour() - tourId not found: " + tourId);
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourService.deleteTour() - internal server error");
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourService.deleteTour() - failed to send request");
        }
    }
}
