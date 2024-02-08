package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "TAGS")
public class Tag {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long tagId;

    @Column(name = "TITLE")
    private String title;

    @Nullable
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "tag")
    @Nullable
    private List<Token> tokens;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "PLACESTAGS",
            joinColumns = { @JoinColumn(name = "TAGID") },
            inverseJoinColumns = { @JoinColumn(name = "PLACEID") }
    )
    @JsonIgnore
    private List<Place> places;
}
