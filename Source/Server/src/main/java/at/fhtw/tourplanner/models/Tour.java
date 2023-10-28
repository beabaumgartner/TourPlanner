package at.fhtw.tourplanner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tour_id")
    private Integer tourId;
    @Column(name = "name")
    private String name;
    @Column(name = "tour_description")
    private String tourDescription;
    @Column(name = "start")
    private String start;
    @Column(name = "destination")
    private String destination;
    @Column(name = "transport_type")
    private String transportType;
    @JsonIgnore
    @Column(name = "tour_distance")
    private Double tourDistance;
    @JsonIgnore
    @Column(name = "estimated_time")
    private Integer estimatedTime;
    @Lob
    @JsonIgnore
    @Column(name = "tour_information")
    private String tourInformation;
    @JsonIgnore
    @Column(name = "popularity")
    private Integer popularity;
    @JsonIgnore
    @Column(name = "child_friendliness")
    private Integer childFriendliness;
    @OneToMany(mappedBy = "tour", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TourLog> tourLogs;
    public Tour(Integer tourId, String name, String tourDescription, String start, String destination, String transportType, Double tourDistance, Integer estimatedTime, String tourInformation, Integer popularity, Integer childFriendliness) {
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
        this.tourLogs = new ArrayList<>();
    }
    public Tour(Integer tourId, String name, String tourDescription, String start, String destination, String transportType, Double tourDistance, Integer estimatedTime, String tourInformation) {
        this.tourId = tourId;
        this.name = name;
        this.tourDescription = tourDescription;
        this.start = start;
        this.destination = destination;
        this.transportType = transportType;
        this.tourDistance = tourDistance;
        this.estimatedTime = estimatedTime;
        this.tourInformation = tourInformation;
        this.popularity = 1;
        this.childFriendliness = 3;
        this.tourLogs = new ArrayList<>();
    }
    public Tour() {
        this.popularity = 1;
        this.childFriendliness = 3;
        this.tourLogs = new ArrayList<>();
    }
    @JsonProperty
    public Integer getTourId() {
        return tourId;
    }
    @JsonIgnore
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
    @JsonProperty
    public Double getTourDistance() {
        return tourDistance;
    }
    @JsonIgnore
    public void setTourDistance(Double tourDistance) {
        this.tourDistance = tourDistance;
    }
    @JsonProperty
    public Integer getEstimatedTime() {
        return estimatedTime;
    }
    @JsonIgnore
    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    @JsonProperty
    public String getTourInformation() {
        return tourInformation;
    }
    @JsonIgnore
    public void setTourInformation(String tourInformation) {
        this.tourInformation = tourInformation;
    }
    @JsonProperty
    public Integer getPopularity() {
        return popularity;
    }
    @JsonIgnore
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }
    @JsonProperty
    public Integer getChildFriendliness() {
        return childFriendliness;
    }
    @JsonIgnore
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
                ", tourInformation='" + "<tourInformation>" + '\'' +
                ", tourLogs=" + tourLogs +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(tourId, tour.tourId) && Objects.equals(name, tour.name) && Objects.equals(tourDescription, tour.tourDescription) && Objects.equals(start, tour.start) && Objects.equals(destination, tour.destination) && Objects.equals(transportType, tour.transportType) && Objects.equals(tourDistance, tour.tourDistance) && Objects.equals(estimatedTime, tour.estimatedTime) && Objects.equals(tourInformation, tour.tourInformation) && Objects.equals(popularity, tour.popularity) && Objects.equals(childFriendliness, tour.childFriendliness);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tourId, name, tourDescription, start, destination, transportType, tourDistance, estimatedTime, tourInformation, popularity, childFriendliness);
    }
    public void updateTourStats() {
        // update popularity
        if(this.tourLogs == null) {
            this.popularity = 1;
        }
        else if (this.tourLogs.size() > 15) {
            this.popularity = 5;
        }
        else if (this.tourLogs.size() > 10) {
            this.popularity = 4;
        }
        else if (this.tourLogs.size() > 5) {
            this.popularity = 3;
        }
        else if (this.tourLogs.size() > 2) {
            this.popularity = 2;
        }
        else {
            this.popularity = 1;
        }

        // update childFriendliness
        if(this.tourLogs == null || this.tourLogs.size() == 0) {
            this.childFriendliness = 3;
        } else {
            Integer averageDifficulty = 0;
            for(TourLog tourLog : this.tourLogs) {
                averageDifficulty = averageDifficulty + tourLog.getDifficulty();
            }
            averageDifficulty = averageDifficulty / this.tourLogs.size();
            this.childFriendliness = 6 - averageDifficulty;
        }
    }
}
