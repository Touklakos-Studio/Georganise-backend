package isima.georganise.app.entity.dao;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.util.List;

@Entity
@Data
@Table(name = "IMAGES")
public class Image {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ImageId;

    @Column(name = "IMAGE")
    private Blob image;

    @Column(name = "NAME")
    private String name;

    @Nullable
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "image")
    @Nullable
    private List<Place> places;
}
