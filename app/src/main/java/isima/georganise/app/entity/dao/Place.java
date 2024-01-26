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
public class Place {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    @Nullable
    private Blob image;

    @Nullable
    private String description;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "places")
    @JsonIgnore
    private List<Tag> tags;
}
