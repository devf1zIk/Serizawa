package kz.bitlab.devf1zIk.service.course;

import kz.bitlab.devf1zIk.dto.ChapterDto;
import kz.bitlab.devf1zIk.entity.Chapter;
import kz.bitlab.devf1zIk.entity.Course;
import kz.bitlab.devf1zIk.exception.ChapterNotFoundException;
import kz.bitlab.devf1zIk.exception.CourseNotFoundException;
import kz.bitlab.devf1zIk.mapper.ChapterMapper;
import kz.bitlab.devf1zIk.repository.ChapterRepository;
import kz.bitlab.devf1zIk.repository.CourseRepository;
import kz.bitlab.devf1zIk.service.chapter.ChapterServiceImpl;
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

public class ChapterServiceImplTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private CourseRepository courseRepository;

    private final ChapterMapper chapterMapper = Mappers.getMapper(ChapterMapper.class);

    @InjectMocks
    private ChapterServiceImpl chapterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chapterService = new ChapterServiceImpl(chapterRepository, courseRepository, chapterMapper);
    }

    @Test
    void getAllChapters_ShouldReturnList() {
        Chapter chapter1 = new Chapter();
        chapter1.setId(1L);
        chapter1.setName("If-Else Conditions");
        Chapter chapter2 = new Chapter();
        chapter2.setId(2L);
        chapter2.setName("Loops");

        when(chapterRepository.findAll()).thenReturn(Arrays.asList(chapter1, chapter2));

        var chapters = chapterService.getAllChapters();
        assertEquals(2, chapters.size());
        verify(chapterRepository, times(1)).findAll();
    }

    @Test
    void getChapterById_WhenExists_ShouldReturnChapter() {
        Chapter chapter = new Chapter();
        chapter.setId(1L);
        chapter.setName("If-Else Conditions");

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));

        ChapterDto dto = chapterService.getChapterById(1L);
        assertEquals("If-Else Conditions", dto.getName());
    }

    @Test
    void getChapterById_WhenNotFound_ShouldThrowException() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ChapterNotFoundException.class, () -> chapterService.getChapterById(1L));
    }

    @Test
    void createChapter_ShouldSaveChapter() {
        ChapterDto dto = new ChapterDto();
        dto.setName("New Chapter");
        dto.setDescription("Описание главы");
        dto.setOrder(1);
        dto.setCourseId(1L);

        Course course = new Course();
        course.setId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(chapterRepository.save(any(Chapter.class))).thenAnswer(i -> i.getArguments()[0]);

        ChapterDto created = chapterService.createChapter(dto);
        assertEquals("New Chapter", created.getName());
        verify(chapterRepository, times(1)).save(any(Chapter.class));
    }

    @Test
    void createChapter_WhenCourseNotFound_ShouldThrowException() {
        ChapterDto dto = new ChapterDto();
        dto.setCourseId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> chapterService.createChapter(dto));
    }

    @Test
    void updateChapter_WhenExists_ShouldUpdateChapter() {
        Chapter chapter = new Chapter();
        chapter.setId(1L);
        chapter.setName("Old Name");
        Course course = new Course();
        course.setId(1L);

        ChapterDto dto = new ChapterDto();
        dto.setName("Updated Name");
        dto.setDescription("Updated Description");
        dto.setOrder(2);
        dto.setCourseId(1L);

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(chapterRepository.save(any(Chapter.class))).thenAnswer(i -> i.getArguments()[0]);

        ChapterDto updated = chapterService.updateChapter(1L, dto);
        assertEquals("Updated Name", updated.getName());
        assertEquals("Updated Description", updated.getDescription());
    }

    @Test
    void updateChapter_WhenChapterNotFound_ShouldThrowException() {
        ChapterDto dto = new ChapterDto();
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ChapterNotFoundException.class, () -> chapterService.updateChapter(1L, dto));
    }

    @Test
    void deleteChapter_WhenExists_ShouldDelete() {
        when(chapterRepository.existsById(1L)).thenReturn(true);
        chapterService.deleteChapter(1L);
        verify(chapterRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteChapter_WhenNotFound_ShouldThrowException() {
        when(chapterRepository.existsById(1L)).thenReturn(false);
        assertThrows(ChapterNotFoundException.class, () -> chapterService.deleteChapter(1L));
    }
}
