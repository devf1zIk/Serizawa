package kz.bitlab.devf1zIk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDto {

    private Long id;

    @NotBlank(message = "Chapter name must not be blank")
    @Size(max = 255, message = "Chapter name must be at most 255 characters")
    private String name;

    @NotBlank(message = "Chapter description must not be blank")
    private String description;

    @NotNull(message = "Chapter order must not be null")
    private Integer order;

    @NotNull(message = "Course ID must not be null")
    private Long courseId;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
