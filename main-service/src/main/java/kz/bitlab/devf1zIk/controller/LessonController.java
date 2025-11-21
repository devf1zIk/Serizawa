package kz.bitlab.devf1zIk.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.bitlab.devf1zIk.dto.LessonDto;
import kz.bitlab.devf1zIk.service.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private static final Logger log = LoggerFactory.getLogger(LessonController.class);

    @Operation(summary = "Получить все уроки")
    @GetMapping
    public List<LessonDto> getAllLessons() {
        log.info("Получение всех уроков");
        return lessonService.getAllLessons();
    }

    @Operation(summary = "Получить урок по ID")
    @GetMapping("/{id}")
    public LessonDto getLessonById(@PathVariable Long id) {
        log.info("Получение урока с ID: {}", id);
        return lessonService.getLessonById(id);
    }

    @Operation(summary = "Создать новый урок")
    @PostMapping
    public ResponseEntity<LessonDto> createLesson(@Valid @RequestBody LessonDto lessonDto) {
        log.info("Создание нового урока: {}", lessonDto.getName());
        return ResponseEntity.ok(lessonService.createLesson(lessonDto));
    }

    @Operation(summary = "Обновить урок по ID")
    @PutMapping("/{id}")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long id,
                                                  @Valid @RequestBody LessonDto lessonDto) {
        log.info("Обновление урока с ID: {}", id);
        return ResponseEntity.ok(lessonService.updateLesson(id, lessonDto));
    }

    @Operation(summary = "Удалить урок по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        log.info("Удаление урока с ID: {}", id);
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
