package kz.bitlab.devf1zIk.service.lesson;

import kz.bitlab.devf1zIk.dto.LessonDto;
import kz.bitlab.devf1zIk.entity.Chapter;
import kz.bitlab.devf1zIk.entity.Lesson;
import kz.bitlab.devf1zIk.exception.ChapterNotFoundException;
import kz.bitlab.devf1zIk.exception.LessonNotFoundException;
import kz.bitlab.devf1zIk.mapper.LessonMapper;
import kz.bitlab.devf1zIk.repository.ChapterRepository;
import kz.bitlab.devf1zIk.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;

    @Override
    public List<LessonDto> getAllLessons() {
        log.info("Получение всех уроков");
        return lessonRepository.findAll().stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDto getLessonById(Long id) {
        log.info("Получение урока с ID: {}", id);
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Урок с ID {} не найден", id);
                    return new LessonNotFoundException("Урок не найден");
                });
        return lessonMapper.toDto(lesson);
    }

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        log.info("Создание нового урока: {}", lessonDto.getName());
        Chapter chapter = chapterRepository.findById(lessonDto.getChapterId())
                .orElseThrow(() -> {
                    log.error("Глава с ID {} не найдена для урока", lessonDto.getChapterId());
                    return new ChapterNotFoundException("Глава не найдена");
                });

        Lesson lesson = lessonMapper.toEntity(lessonDto);
        lesson.setChapter(chapter);
        lesson.setCreatedTime(LocalDateTime.now());
        lesson.setUpdatedTime(LocalDateTime.now());

        Lesson savedLesson = lessonRepository.save(lesson);
        log.info("Урок '{}' успешно создан с ID: {}", savedLesson.getName(), savedLesson.getId());
        return lessonMapper.toDto(savedLesson);
    }

    @Override
    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
        log.info("Обновление урока с ID: {}", id);
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Урок с ID {} не найден для обновления", id);
                    return new LessonNotFoundException("Урок не найден");
                });

        Chapter chapter = chapterRepository.findById(lessonDto.getChapterId())
                .orElseThrow(() -> {
                    log.error("Глава с ID {} не найдена для обновления урока", lessonDto.getChapterId());
                    return new LessonNotFoundException("Глава не найдена");
                });

        lesson.setName(lessonDto.getName());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setContent(lessonDto.getContent());
        lesson.setOrder(lessonDto.getOrder());
        lesson.setChapter(chapter);
        lesson.setUpdatedTime(LocalDateTime.now());

        Lesson updatedLesson = lessonRepository.save(lesson);
        log.info("Урок с ID {} успешно обновлен", id);
        return lessonMapper.toDto(updatedLesson);
    }

    @Override
    public void deleteLesson(Long id) {
        log.info("Удаление урока с ID: {}", id);
        if (!lessonRepository.existsById(id)) {
            log.error("Урок с ID {} не найден для удаления", id);
            throw new LessonNotFoundException("Урок не найден");
        }
        lessonRepository.deleteById(id);
        log.info("Урок с ID {} успешно удален", id);
    }
}
