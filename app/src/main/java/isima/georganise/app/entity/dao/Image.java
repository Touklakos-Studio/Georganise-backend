package isima.georganise.app.entity.dao;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.util.List;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private Blob image;

    private String name;

    @Nullable
    private String description;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "image")
    @Nullable
    private List<Place> places;
}
