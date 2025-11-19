package kz.bitlab.devf1zIk.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.bitlab.devf1zIk.dto.CourseDto;
import kz.bitlab.devf1zIk.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Получить все курсы")
    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourses();
    }

    @Operation(summary = "Получить курс по ID")
    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @Operation(summary = "Создать новый курс")
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.createCourse(courseDto));
    }

    @Operation(summary = "Обновить курс по ID")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id,
                                                  @Valid @RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDto));
    }

    @Operation(summary = "Удалить курс по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}