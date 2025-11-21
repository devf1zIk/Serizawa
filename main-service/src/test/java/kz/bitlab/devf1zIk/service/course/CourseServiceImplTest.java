package kz.bitlab.devf1zIk.service.course;

import kz.bitlab.devf1zIk.dto.CourseDto;
import kz.bitlab.devf1zIk.entity.Course;
import kz.bitlab.devf1zIk.exception.CourseNotFoundException;
import kz.bitlab.devf1zIk.mapper.CourseMapper;
import kz.bitlab.devf1zIk.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseRepository, courseMapper);
    }

    @Test
    void getAllCourses_ShouldReturnList() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Java Developer");
        course1.setCreatedTime(LocalDateTime.now());
        course1.setUpdatedTime(LocalDateTime.now());

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Python Developer");
        course2.setCreatedTime(LocalDateTime.now());
        course2.setUpdatedTime(LocalDateTime.now());

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        var courses = courseService.getAllCourses();
        assertEquals(2, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getCourseById_WhenCourseExists_ShouldReturnCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setName("Java Developer");
        course.setCreatedTime(LocalDateTime.now());
        course.setUpdatedTime(LocalDateTime.now());

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseDto dto = courseService.getCourseById(1L);
        assertEquals("Java Developer", dto.getName());
    }

    @Test
    void getCourseById_WhenCourseNotFound_ShouldThrowException() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(1L));
    }

    @Test
    void createCourse_ShouldSaveCourse() {
        CourseDto dto = new CourseDto();
        dto.setName("New Course");
        dto.setDescription("Описание курса");

        Course course = courseMapper.toEntity(dto);
        course.setId(1L);
        course.setCreatedTime(LocalDateTime.now());
        course.setUpdatedTime(LocalDateTime.now());

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDto created = courseService.createCourse(dto);
        assertEquals("New Course", created.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }
}
