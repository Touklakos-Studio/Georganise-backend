package isima.georganise.app.entity.dao;

import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.util.Right;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * This class represents the Token entity with all its properties.
 * It uses the @Entity annotation to indicate that it is a JPA entity.
 * Lombok's @Data annotation is used to automatically generate getters, setters, equals, hash and toString methods.
 * @NoArgsConstructor is a Lombok annotation to generate a constructor with no parameters.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "TOKENS")
public class Token implements Serializable {

    /**
     * The unique ID of the token. It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long tokenId;

    /**
     * The value of the token. It is a unique UUID.
     */
    @Column(name = "TOKEN", updatable = false, nullable = false, unique = true)
    private UUID tokenValue;

    /**
     * The access right associated with the token.
     */
    @Column(name = "ACCESSRIGHT", nullable = false)
    private Right accessRight;

    /**
     * The ID of the user who created the token.
     */
    @Column(name = "CREATORID", updatable = false, nullable = false)
    private Long creatorId;

    /**
     * The ID of the user associated with the token.
     */
    @Column(name = "USERID")
    private Long userId;

    /**
     * The ID of the tag associated with the token.
     */
    @Column(name = "TAGID", updatable = false, nullable = false)
    private Long tagId;

    /**
     * Constructor for the Token class.
     * @param token the token creation DTO
     * @param creatorId the creator ID
     * @param userId the user ID
     */
    public Token(@NotNull TokenCreationDTO token, Long creatorId, Long userId) {
        this.creatorId = creatorId;
        this.tokenValue = UUID.randomUUID();
        this.accessRight = token.getAccessRight();
        this.tagId = token.getTagId();
        this.userId = userId;
    }
}