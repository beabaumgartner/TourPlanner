package at.fhtw.tourplanner.models;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class MapQuestData {
    private Double tourDistance;
    private Integer estimatedTime;
    public MapQuestData(Double tourDistance, Integer estimatedTime) {
        DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        this.tourDistance = Double.valueOf(df.format(tourDistance));
        this.estimatedTime = estimatedTime;
    }
    public MapQuestData() { }
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
    @Override
    public String toString() {
        return "MapQuestResponseData{" +
                "tourDistance=" + tourDistance +
                ", estimatedTime=" + estimatedTime +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapQuestData that = (MapQuestData) o;
        return Objects.equals(tourDistance, that.tourDistance) && Objects.equals(estimatedTime, that.estimatedTime);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tourDistance, estimatedTime);
    }
}
