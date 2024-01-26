package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Nullable
    private String description;

    @OneToMany(mappedBy = "tag")
    @Nullable
    private List<Token> tokens;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Place> places;
}
