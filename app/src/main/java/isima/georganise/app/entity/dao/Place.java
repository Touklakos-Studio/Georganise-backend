package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import isima.georganise.app.entity.util.GPS;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

@Data
@Entity
@Table(name = "PLACES")
public class Place {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long placeId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LONGITUDE")
    private BigDecimal longitude;

    @Column(name = "LATITUDE")
    private BigDecimal latitude;

    @Nullable
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "imageId", nullable = false)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "PLACESTAGS",
            joinColumns = { @JoinColumn(name = "PLACEID") },
            inverseJoinColumns = { @JoinColumn(name = "TAGID") }
    )
    @JsonIgnore
    private List<Tag> tags;
}
