package at.fhtw.tourplanner.repositories;

import at.fhtw.tourplanner.TourPlannerApplication;
import at.fhtw.tourplanner.exceptions.BadRequestException;
import at.fhtw.tourplanner.models.MapQuestData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class MapQuestRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public MapQuestData findMapQuestData(String start, String destination, String transportType) {
        try {
            URI uri = new URI("https://www.mapquestapi.com/directions/v2/route?from=" + URLEncoder.encode(start)
                    + "&to=" + URLEncoder.encode(destination)
                    + "&routeType=" + URLEncoder.encode(transportType)
                    + "&key=" + URLEncoder.encode(TourPlannerApplication.API_KEY)
                    + "&unit=k");

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode routeNode = rootNode.get("route");

            Double distance = routeNode.get("distance").asDouble();
            Integer time = routeNode.get("time").asInt();

            MapQuestData mapQuestData = new MapQuestData(distance, time);
            return mapQuestData;
        }
        catch(Exception e) {
            // e.printStackTrace();
            throw new BadRequestException("MapQuestRepository.findMapQuestData() - bad request");
        }
    }
    public String findMapQuestMap(String start, String destination) {
        try {
            URI uri = new URI("https://www.mapquestapi.com/staticmap/v5/map?start=" + URLEncoder.encode(start)
                    + "&end=" + URLEncoder.encode(destination)
                    + "&key=" + URLEncoder.encode(TourPlannerApplication.API_KEY));

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<byte[]> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

            byte[] imageBytes = response.body();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

            return encodedImage;
        }
        catch (Exception e) {
            // e.printStackTrace();
            throw new BadRequestException("MapQuestRepository.findMapQuestMap() - bad request");
        }
    }
}
