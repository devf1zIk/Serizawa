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
public class LessonDto {

    private Long id;

    @NotBlank(message = "Lesson name must not be blank")
    @Size(max = 255, message = "Lesson name must be at most 255 characters")
    private String name;

    @NotBlank(message = "Lesson description must not be blank")
    private String description;

    @NotBlank(message = "Lesson content must not be blank")
    private String content;

    @NotNull(message = "Lesson order must not be null")
    private Integer order;

    @NotNull(message = "Chapter ID must not be null")
    private Long chapterId;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
