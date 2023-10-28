package at.fhtw.tourplanner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "tour_logs")
public class TourLog {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tour_log_id")
    private Integer tourLogId;
    @JsonIgnore
    @Column(name = "date")
    private String date;
    @Column(name = "comment")
    private String comment;
    @Column(name = "difficulty")
    private Integer difficulty;
    @Column(name = "total_time")
    private Integer totalTime;
    @Column(name = "rating")
    private Integer rating;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tour_id", referencedColumnName = "tour_id")
    private Tour tour;
    public TourLog(Integer tourLogId, String comment, Integer difficulty, Integer totalTime, Integer rating) {
        this.tourLogId = tourLogId;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
        this.date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
    public TourLog() {
        this.date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
    @JsonProperty
    public Integer getTourLogId() {
        return tourLogId;
    }
    @JsonIgnore
    public void setTourLogId(Integer tourLogId) {
        this.tourLogId = tourLogId;
    }
    @JsonProperty
    public String getDate() {
        return date;
    }
    @JsonIgnore
    public void setDate(String date) {
        this.date = date;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
    public Integer getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Tour getTour() {
        return tour;
    }
    public void setTour(Tour tour) {
        this.tour = tour;
    }
    @Override
    public String toString() {
        return "TourLog{" +
                "tourLogId=" + tourLogId +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                ", difficulty=" + difficulty +
                ", totalTime=" + totalTime +
                ", rating=" + rating +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourLog tourLog = (TourLog) o;
        return Objects.equals(tourLogId, tourLog.tourLogId) && Objects.equals(date, tourLog.date) && Objects.equals(comment, tourLog.comment) && Objects.equals(difficulty, tourLog.difficulty) && Objects.equals(totalTime, tourLog.totalTime) && Objects.equals(rating, tourLog.rating);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tourLogId, date, comment, difficulty, totalTime, rating);
    }
}
