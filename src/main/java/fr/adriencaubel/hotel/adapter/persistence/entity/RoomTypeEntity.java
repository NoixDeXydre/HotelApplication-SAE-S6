package fr.adriencaubel.hotel.adapter.persistence.entity;

import fr.adriencaubel.hotel.domain.entity.RoomTypePrice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class RoomTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public int totalRooms;

    @OneToMany(mappedBy = "roomType",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<RoomTypePrice> prices = new ArrayList<>();

    public RoomTypeEntity() {

    }

    public RoomTypeEntity(Long id, List<RoomTypePrice> prices, String name, int totalRooms) {
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
}
