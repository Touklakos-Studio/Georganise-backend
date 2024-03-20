package isima.georganise.app.entity.dao;

import isima.georganise.app.entity.dto.UserCreationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * This class represents the User entity with all its properties.
 * It uses the @Entity annotation to indicate that it is a JPA entity.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "USERS")
public class User implements Serializable {

    /**
     * The unique ID of the user. It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long userId;

    /**
     * The nickname of the user. It is unique.
     */
    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;

    /**
     * The password of the user.
     */
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    /**
     * The email of the user. It is unique.
     */
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    /**
     * The authentication token of the user. It is unique.
     */
    @Column(name = "TOKEN", unique = true)
    private UUID authToken;

    /**
     * Constructor for the User class.
     * @param user the user creation DTO
     */
    public User(@NotNull UserCreationDTO user) {
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authToken = UUID.randomUUID();
    }
}