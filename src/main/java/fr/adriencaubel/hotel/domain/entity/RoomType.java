package fr.adriencaubel.hotel.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class RoomType {

    public Long id;
    public String name;
    public int totalRooms;

    private List<RoomTypePrice> prices = new ArrayList<>();

    public RoomType() {

    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
    }

    public void setTotalRooms(int totalRooms) {
    }
}
