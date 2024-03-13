package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.*;
import isima.georganise.app.entity.dto.PlaceCreationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "PLACES")
public class Place implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long placeId;

    @Column(name = "LATITUDE", nullable = false)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private BigDecimal longitude;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGEID", nullable = false)
    private Long imageId;

    @Column(name = "USERID", updatable = false, nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "place")
    @JsonManagedReference
    private List<PlaceTag> placeTags;

    public Place(PlaceCreationDTO placeCreationDTO, Long userId) {
        this.latitude = placeCreationDTO.getLatitude();
        this.longitude = placeCreationDTO.getLongitude();
        this.name = placeCreationDTO.getName();
        this.description = placeCreationDTO.getDescription();
        this.imageId = placeCreationDTO.getImageId();
        this.userId = userId;
    }
}
