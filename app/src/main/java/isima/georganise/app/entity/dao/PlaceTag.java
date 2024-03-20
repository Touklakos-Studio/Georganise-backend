package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * This class represents the PlaceTag entity with all its properties.
 * It uses the @Entity annotation to indicate that it is a JPA entity.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "PLACESTAGS")
public class PlaceTag implements Serializable {

    /**
     * The unique ID of the place tag. It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long placeTagId;

    /**
     * The place associated with the place tag.
     * It uses the @ManyToOne annotation to indicate the many-to-one relationship with the Place entity.
     * @JsonBackReference is used to avoid the infinite loop problem during serialization.
     */
    @ManyToOne
    @JoinColumn(name = "PLACEID", updatable = false, nullable = false)
    @JsonBackReference
    private Place place;

    /**
     * The tag associated with the place tag.
     * It uses the @ManyToOne annotation to indicate the many-to-one relationship with the Tag entity.
     * @JsonBackReference is used to avoid the infinite loop problem during serialization.
     */
    @ManyToOne
    @JoinColumn(name = "TAGID", updatable = false, nullable = false)
    @JsonBackReference
    private Tag tag;

    /**
     * Constructor for the PlaceTag class.
     * @param place the place
     * @param tag the tag
     */
    public PlaceTag(Place place, Tag tag) {
        this.place = place;
        this.tag = tag;
    }

    /**
     * Override the toString method to provide a custom string representation of the object.
     * @return a string representation of the object
     */
    public @NotNull String toString() {
        return "PlaceTag{" +
                "placeTagId=" + placeTagId +
                ", placeId=" + place.getPlaceId() +
                ", tagId=" + tag.getTagId() +
                '}';
    }
}