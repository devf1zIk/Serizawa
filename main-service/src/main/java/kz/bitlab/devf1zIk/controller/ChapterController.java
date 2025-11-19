package kz.bitlab.devf1zIk.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.bitlab.devf1zIk.dto.ChapterDto;
import kz.bitlab.devf1zIk.service.chapter.ChapterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;
    private static final Logger log = LoggerFactory.getLogger(ChapterController.class);

    @Operation(summary = "Получить все главы")
    @GetMapping
    public List<ChapterDto> getAllChapters() {
        log.info("Получение всех глав");
        return chapterService.getAllChapters();
    }

    @Operation(summary = "Получить главу по ID")
    @GetMapping("/{id}")
    public ChapterDto getChapterById(@PathVariable Long id) {
        log.info("Получение главы с ID: {}", id);
        return chapterService.getChapterById(id);
    }

    @Operation(summary = "Создать новую главу")
    @PostMapping
    public ResponseEntity<ChapterDto> createChapter(@Valid @RequestBody ChapterDto chapterDto) {
        log.info("Создание новой главы: {}", chapterDto.getName());
        return ResponseEntity.ok(chapterService.createChapter(chapterDto));
    }

    @Operation(summary = "Обновить главу по ID")
    @PutMapping("/{id}")
    public ResponseEntity<ChapterDto> updateChapter(@PathVariable Long id,
                                                    @Valid @RequestBody ChapterDto chapterDto) {
        log.info("Обновление главы с ID: {}", id);
        return ResponseEntity.ok(chapterService.updateChapter(id, chapterDto));
    }

    @Operation(summary = "Удалить главу по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        log.info("Удаление главы с ID: {}", id);
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
}
