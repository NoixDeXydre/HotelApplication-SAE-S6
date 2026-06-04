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

    public RoomType(Long id, List<RoomTypePrice> prices, String name, int totalRooms) {
        this.id = id;
        this.prices = prices;
        this.name = name;
        this.totalRooms = totalRooms;
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

    public List<RoomTypePrice> getPrices() {
        return prices;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }
}
