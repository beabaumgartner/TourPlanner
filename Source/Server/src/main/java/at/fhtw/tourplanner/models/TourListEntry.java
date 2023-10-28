package at.fhtw.tourplanner.models;

import java.util.Objects;

public class TourListEntry {
    private Integer tourId;
    private String name;
    public TourListEntry(Integer tourId, String name) {
        this.tourId = tourId;
        this.name = name;
    }
    public TourListEntry() { }
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
    @Override
    public String toString() {
        return "TourListEntry{" +
                "tourId=" + tourId +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourListEntry that = (TourListEntry) o;
        return Objects.equals(tourId, that.tourId) && Objects.equals(name, that.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tourId, name);
    }
}
