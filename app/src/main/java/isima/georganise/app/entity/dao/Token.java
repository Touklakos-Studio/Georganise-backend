package isima.georganise.app.entity.dao;

import isima.georganise.app.entity.util.Right;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "TOKENS")
public class Token {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long tokenId;

    @Column(name = "TOKEN")
    private UUID token;

    @Column(name = "ACCESSRIGHT")
    private Right accessRight;

    @ManyToOne
    @JoinColumn(name = "TAGID", nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false, insertable = false, updatable = false)
    @Nullable
    private User user;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false, insertable = false, updatable = false)
    @Nullable
    private User creator;
}
