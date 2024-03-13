package isima.georganise.app.entity.dao;

import com.fasterxml.jackson.annotation.*;
import isima.georganise.app.entity.dto.ImageCreationDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "IMAGES")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private Long imageId;

    @Column(name = "USERID", updatable = false, nullable = false)
    private Long userId;

    @Column(name = "IMAGE", updatable = false, nullable = false)
    private byte[] imageValue;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PUBLIC", nullable = false)
    private boolean isPublic;

    public Image(ImageCreationDTO imageCreationDTO, Long userId) {
        this.userId = userId;
        this.imageValue = imageCreationDTO.getImageValue();
        this.name = imageCreationDTO.getName();
        this.description = imageCreationDTO.getDescription();
        this.isPublic = imageCreationDTO.isPublic();
    }
}
