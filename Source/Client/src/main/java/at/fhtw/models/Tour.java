package at.fhtw.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

public class Tour {
    private Integer tourId;
    private String name;
    private String tourDescription;
    private String start;
    private String destination;
    private String transportType;
    private Double tourDistance;
    private Integer estimatedTime;
    private String tourInformation;
    private Integer popularity;
    private Integer childFriendliness;
    private List<TourLog> tourLogs;
    public Tour(Integer tourId, String name, String tourDescription, String start, String destination, String transportType, Double tourDistance, Integer estimatedTime, String tourInformation, Integer popularity, Integer childFriendliness, List<TourLog> tourLogs) {
        this.tourId = tourId;
        this.name = name;
        this.tourDescription = tourDescription;
        this.start = start;
        this.destination = destination;
        this.transportType = transportType;
        this.tourDistance = tourDistance;
        this.estimatedTime = estimatedTime;
        this.tourInformation = tourInformation;
        this.popularity = popularity;
        this.childFriendliness = childFriendliness;
        this.tourLogs = tourLogs;
    }
    public Tour(String name, String tourDescription, String start, String destination, String transportType) {
        this.name = name;
        this.tourDescription = tourDescription;
        this.start = start;
        this.destination = destination;
        this.transportType = transportType;
    }
    public Tour() { }
    public Integer getTourId() {
        return tourId;
    }
    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTourDescription() {
        return tourDescription;
    }
    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getTransportType() {
        return transportType;
    }
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
    public Double getTourDistance() {
        return tourDistance;
    }
    public void setTourDistance(Double tourDistance) {
        this.tourDistance = tourDistance;
    }
    public Integer getEstimatedTime() {
        return estimatedTime;
    }
    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    public String getTourInformation() {
        return tourInformation;
    }
    public void setTourInformation(String tourInformation) {
        this.tourInformation = tourInformation;
    }
    public Integer getPopularity() {
        return popularity;
    }
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }
    public Integer getChildFriendliness() {
        return childFriendliness;
    }
    public void setChildFriendliness(Integer childFriendliness) {
        this.childFriendliness = childFriendliness;
    }
    public List<TourLog> getTourLogs() {
        return tourLogs;
    }
    public void setTourLogs(List<TourLog> tourLogs) {
        this.tourLogs = tourLogs;
    }
    @Override
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", name='" + name + '\'' +
                ", tourDescription='" + tourDescription + '\'' +
                ", start='" + start + '\'' +
                ", destination='" + destination + '\'' +
                ", transportType='" + transportType + '\'' +
                ", tourDistance=" + tourDistance +
                ", estimatedTime=" + estimatedTime +
                ", popularity=" + popularity +
                ", childFriendliness=" + childFriendliness +
                ", tourLogs=" + tourLogs +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(tourId, tour.tourId) && Objects.equals(name, tour.name) && Objects.equals(tourDescription, tour.tourDescription) && Objects.equals(start, tour.start) && Objects.equals(destination, tour.destination) && Objects.equals(transportType, tour.transportType) && Objects.equals(tourDistance, tour.tourDistance) && Objects.equals(estimatedTime, tour.estimatedTime) && Objects.equals(tourInformation, tour.tourInformation) && Objects.equals(tourLogs, tour.tourLogs);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tourId, name, tourDescription, start, destination, transportType, tourDistance, estimatedTime, tourInformation, tourLogs);
    }
}
