package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.*;
import isima.georganise.app.entity.dto.UserCreationDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long userId;

    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "TOKEN", unique = true)
    private String authToken;

    public User(UserCreationDTO user) {
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }
}
