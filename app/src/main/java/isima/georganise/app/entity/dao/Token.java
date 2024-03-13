package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import isima.georganise.app.entity.dto.TokenCreationDTO;
import isima.georganise.app.entity.util.Right;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "TOKENS")
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long tokenId;

    @Column(name = "TOKEN", updatable = false, nullable = false, unique = true)
    private UUID tokenValue;

    @Column(name = "ACCESSRIGHT", nullable = false)
    private Right accessRight;

    @Column(name = "CREATORID", updatable = false, nullable = false)
    private Long creatorId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "TAGID", updatable = false, nullable = false)
    private Long tagId;

    public Token(TokenCreationDTO token) {
        this.tokenValue = UUID.randomUUID();
        this.accessRight = token.getAccessRight();
        this.userId = token.getUserId();
        this.tagId = token.getTagId();
    }
}
