package at.fhtw.services;

import at.fhtw.exceptions.*;
import at.fhtw.models.TourLog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TourLogService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public TourLog createTourLog(Integer tourId, TourLog tourLog) {
        try {
            String jsonString = objectMapper.writeValueAsString(tourLog);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours/" + tourId + "/tourlogs"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourLogService.createTourLog() - not found");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourLogService.createTour() - internal server error");
            }

            TourLog newTourLog = objectMapper.readValue(httpResponse.body(), TourLog.class);
            return newTourLog;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourLogService.createTourLog() - failed to send request");
        }
    }
    public List<TourLog> getTourLogs(Integer tourId) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tours/" + tourId + "/tourlogs"))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 204) {
                throw new NoContentException("TourLogService.getTourList() - no content");
            }
            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourLogService.getTourLogs() - not found");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourLogService.getTourLogs() - internal server error");
            }

            List <TourLog> tourLogs = List.of(objectMapper.readValue(httpResponse.body(), TourLog[].class));
            return tourLogs;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourLogService.getTourLogs() - failed to send request");
        }
    }
    public TourLog getTourLog(Integer tourLogId) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tourlogs/" + tourLogId))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourLogService.getTourLog() - not found");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourLogService.getTourLog() - internal server error");
            }

            TourLog tourLog = objectMapper.readValue(httpResponse.body(), TourLog.class);
            return tourLog;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourLogService.getTourLog() - failed to send request");
        }
    }
    public TourLog updateTourLog(Integer tourLogId, TourLog tourlog) {
        try {
            String jsonString = objectMapper.writeValueAsString(tourlog);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tourlogs/" + tourLogId))
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourLogService.updateTourLog() - not found");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourLogService.updateTourLog() - internal server error");
            }

            TourLog newTourLog = objectMapper.readValue(httpResponse.body(), TourLog.class);
            return newTourLog;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourLogService.updateTourLog() - failed to send request");
        }
    }
    public void deleteTourLog(Integer tourLogId) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:10001/tourlogs/" + tourLogId))
                    .DELETE()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(httpResponse.statusCode() == 404) {
                throw new NotFoundException("TourLogService.deleteTourLog() - not found");
            }
            if(httpResponse.statusCode() == 500) {
                throw new InternalServerErrorException("TourLogService.deleteTourLog() - internal server error");
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // e.printStackTrace();
            throw new FailedToSendRequestException("TourLogService.deleteTourLog() - failed to send request");
        }
    }
}
