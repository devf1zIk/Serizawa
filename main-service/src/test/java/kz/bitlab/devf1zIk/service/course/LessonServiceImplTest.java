package kz.bitlab.devf1zIk.service.course;

import kz.bitlab.devf1zIk.dto.LessonDto;
import kz.bitlab.devf1zIk.entity.Chapter;
import kz.bitlab.devf1zIk.entity.Lesson;
import kz.bitlab.devf1zIk.exception.ChapterNotFoundException;
import kz.bitlab.devf1zIk.exception.LessonNotFoundException;
import kz.bitlab.devf1zIk.mapper.LessonMapper;
import kz.bitlab.devf1zIk.repository.ChapterRepository;
import kz.bitlab.devf1zIk.repository.LessonRepository;
import kz.bitlab.devf1zIk.service.lesson.LessonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ChapterRepository chapterRepository;

    private final LessonMapper lessonMapper = Mappers.getMapper(LessonMapper.class);

    @InjectMocks
    private LessonServiceImpl lessonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lessonService = new LessonServiceImpl(lessonRepository, chapterRepository, lessonMapper);
    }

    @Test
    void getAllLessons_ShouldReturnList() {
        Lesson lesson1 = new Lesson();
        lesson1.setId(1L);
        lesson1.setName("Lecture 1");

        Lesson lesson2 = new Lesson();
        lesson2.setId(2L);
        lesson2.setName("Practice 1");

        when(lessonRepository.findAll()).thenReturn(Arrays.asList(lesson1, lesson2));

        var lessons = lessonService.getAllLessons();
        assertEquals(2, lessons.size());
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    void getLessonById_WhenExists_ShouldReturnLesson() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setName("Lecture 1");

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        LessonDto dto = lessonService.getLessonById(1L);
        assertEquals("Lecture 1", dto.getName());
    }

    @Test
    void getLessonById_WhenNotFound_ShouldThrowException() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LessonNotFoundException.class, () -> lessonService.getLessonById(1L));
    }

    @Test
    void createLesson_ShouldSaveLesson() {
        LessonDto dto = new LessonDto();
        dto.setName("New Lesson");
        dto.setDescription("Описание урока");
        dto.setContent("<p>Content</p>");
        dto.setOrder(1);
        dto.setChapterId(1L);

        Chapter chapter = new Chapter();
        chapter.setId(1L);

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(lessonRepository.save(any(Lesson.class))).thenAnswer(i -> i.getArguments()[0]);

        LessonDto created = lessonService.createLesson(dto);
        assertEquals("New Lesson", created.getName());
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }

    @Test
    void createLesson_WhenChapterNotFound_ShouldThrowException() {
        LessonDto dto = new LessonDto();
        dto.setChapterId(1L);

        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ChapterNotFoundException.class, () -> lessonService.createLesson(dto));
    }

    @Test
    void updateLesson_WhenExists_ShouldUpdateLesson() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setName("Old Name");
        Chapter chapter = new Chapter();
        chapter.setId(1L);

        LessonDto dto = new LessonDto();
        dto.setName("Updated Name");
        dto.setDescription("Updated Description");
        dto.setContent("<p>Updated</p>");
        dto.setOrder(2);
        dto.setChapterId(1L);

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(lessonRepository.save(any(Lesson.class))).thenAnswer(i -> i.getArguments()[0]);

        LessonDto updated = lessonService.updateLesson(1L, dto);
        assertEquals("Updated Name", updated.getName());
        assertEquals("Updated Description", updated.getDescription());
    }

    @Test
    void updateLesson_WhenLessonNotFound_ShouldThrowException() {
        LessonDto dto = new LessonDto();
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LessonNotFoundException.class, () -> lessonService.updateLesson(1L, dto));
    }

    @Test
    void deleteLesson_WhenExists_ShouldDelete() {
        when(lessonRepository.existsById(1L)).thenReturn(true);
        lessonService.deleteLesson(1L);
        verify(lessonRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteLesson_WhenNotFound_ShouldThrowException() {
        when(lessonRepository.existsById(1L)).thenReturn(false);
        assertThrows(LessonNotFoundException.class, () -> lessonService.deleteLesson(1L));
    }
}