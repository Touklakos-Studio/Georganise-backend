package isima.georganise.app.entity.dao;

import isima.georganise.app.entity.util.Right;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private UUID token;


    private Right right;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    @Nullable
    private User user;
}
