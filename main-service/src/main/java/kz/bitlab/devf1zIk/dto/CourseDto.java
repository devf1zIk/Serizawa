package kz.bitlab.devf1zIk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private Long id;

    @NotBlank(message = "Course name must not be blank")
    @Size(max = 255, message = "Course name must be at most 255 characters")
    private String name;

    @NotBlank(message = "Course description must not be blank")
    private String description;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
