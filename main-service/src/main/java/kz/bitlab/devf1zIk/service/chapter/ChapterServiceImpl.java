package kz.bitlab.devf1zIk.service.chapter;

import kz.bitlab.devf1zIk.dto.ChapterDto;
import kz.bitlab.devf1zIk.entity.Chapter;
import kz.bitlab.devf1zIk.entity.Course;
import kz.bitlab.devf1zIk.exception.ChapterNotFoundException;
import kz.bitlab.devf1zIk.exception.CourseNotFoundException;
import kz.bitlab.devf1zIk.mapper.ChapterMapper;
import kz.bitlab.devf1zIk.repository.ChapterRepository;
import kz.bitlab.devf1zIk.repository.CourseRepository;
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
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public List<ChapterDto> getAllChapters() {
        log.info("Получение всех глав");
        return chapterRepository.findAll().stream()
                .map(chapterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChapterDto getChapterById(Long id) {
        log.info("Получение главы с ID: {}", id);
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Глава с ID {} не найдена", id);
                    return new ChapterNotFoundException("Глава не найдена");
                });
        return chapterMapper.toDto(chapter);
    }

    @Override
    public ChapterDto createChapter(ChapterDto chapterDto) {
        log.info("Создание новой главы: {}", chapterDto.getName());
        Course course = courseRepository.findById(chapterDto.getCourseId())
                .orElseThrow(() -> {
                    log.error("Курс с ID {} не найден для главы", chapterDto.getCourseId());
                    return new CourseNotFoundException("Курс не найден");
                });

        Chapter chapter = chapterMapper.toEntity(chapterDto);
        chapter.setCourse(course);
        chapter.setCreatedTime(LocalDateTime.now());
        chapter.setUpdatedTime(LocalDateTime.now());

        Chapter savedChapter = chapterRepository.save(chapter);
        log.info("Глава '{}' успешно создана с ID: {}", savedChapter.getName(), savedChapter.getId());
        return chapterMapper.toDto(savedChapter);
    }

    @Override
    public ChapterDto updateChapter(Long id, ChapterDto chapterDto) {
        log.info("Обновление главы с ID: {}", id);
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Глава с ID {} не найдена для обновления", id);
                    return new ChapterNotFoundException("Глава не найдена");
                });

        Course course = courseRepository.findById(chapterDto.getCourseId())
                .orElseThrow(() -> {
                    log.error("Курс с ID {} не найден для обновления главы", chapterDto.getCourseId());
                    return new ChapterNotFoundException("Курс не найден");
                });

        chapter.setName(chapterDto.getName());
        chapter.setDescription(chapterDto.getDescription());
        chapter.setOrder(chapterDto.getOrder());
        chapter.setCourse(course);
        chapter.setUpdatedTime(LocalDateTime.now());

        Chapter updatedChapter = chapterRepository.save(chapter);
        log.info("Глава с ID {} успешно обновлена", id);
        return chapterMapper.toDto(updatedChapter);
    }

    @Override
    public void deleteChapter(Long id) {
        log.info("Удаление главы с ID: {}", id);
        if (!chapterRepository.existsById(id)) {
            log.error("Глава с ID {} не найдена для удаления", id);
            throw new ChapterNotFoundException("Глава не найдена");
        }
        chapterRepository.deleteById(id);
        log.info("Глава с ID {} успешно удалена", id);
    }
}
