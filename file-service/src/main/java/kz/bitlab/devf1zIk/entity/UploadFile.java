package kz.bitlab.devf1zIk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_files")
@Getter
@Setter
public class UploadFile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "initial_name")
    private String initialName;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
