package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Place> places;

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<Token> tokens;
}
