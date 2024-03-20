package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import isima.georganise.app.entity.dto.TagCreationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the Tag entity with all its properties.
 * It uses the @Entity annotation to indicate that it is a JPA entity.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "TAGS")
public class Tag implements Serializable {

    /**
     * The unique ID of the tag. It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long tagId;

    /**
     * The title of the tag.
     */
    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    /**
     * The description of the tag.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * The ID of the user who created the tag.
     */
    @Column(name = "USERID", updatable = false, nullable = false)
    private Long userId;

    /**
     * The list of place tags associated with the tag.
     * It uses the @OneToMany annotation to indicate the one-to-many relationship with the PlaceTag entity.
     * @JsonManagedReference is used to avoid the infinite loop problem during serialization.
     */
    @OneToMany(mappedBy = "tag")
    @JsonManagedReference
    private List<PlaceTag> placeTags;

    /**
     * Constructor for the Tag class.
     * @param tag the tag creation DTO
     * @param userId the user ID
     */
    public Tag(@NotNull TagCreationDTO tag, Long userId) {
        this.title = tag.getTitle();
        this.description = tag.getDescription();
        this.userId = userId;
    }
}