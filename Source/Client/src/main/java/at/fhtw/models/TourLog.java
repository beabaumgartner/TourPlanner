package at.fhtw.models;

import java.util.Objects;

public class TourLog {
    private Integer tourLogId;
    private String date;
    private String comment;
    private Integer difficulty;
    private Integer totalTime;
    private Integer rating;
    public TourLog(Integer tourLogId, String date, String comment, Integer difficulty, Integer totalTime, Integer rating) {
        this.tourLogId = tourLogId;
        this.date = date;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }
    public TourLog(Integer tourLogId, String comment, Integer difficulty, Integer totalTime, Integer rating) {
        this.tourLogId = tourLogId;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }
    public TourLog(String comment, Integer difficulty, Integer totalTime, Integer rating) {
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }
    public TourLog() { }
    public Integer getTourLogId() {
        return tourLogId;
    }
    public void setTourLogId(Integer tourLogId) {
        this.tourLogId = tourLogId;
    }

    public String getDate() {
        return date;
    }
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
