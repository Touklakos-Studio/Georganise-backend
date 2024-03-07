package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long userId;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "TOKEN")
    private String authToken;

    @OneToMany(mappedBy = "user")
    private List<Place> places;

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<Token> tokens;

    @OneToMany(mappedBy = "creator")
    @Nullable
    private List<Token> tokensCreated;

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<Image> images;

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<Tag> tags;
}
