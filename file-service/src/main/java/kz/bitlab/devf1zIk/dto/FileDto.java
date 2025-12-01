package kz.bitlab.devf1zIk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {

    private int id;
    private String fileName;
    private String initialName;
    private String mimeType;
    private long fileSize;
    private LocalDateTime createdAt;
}
