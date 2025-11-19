package kz.bitlab.devf1zIk.service.course;

import kz.bitlab.devf1zIk.dto.CourseDto;
import kz.bitlab.devf1zIk.entity.Course;
import kz.bitlab.devf1zIk.exception.CourseNotFoundException;
import kz.bitlab.devf1zIk.mapper.CourseMapper;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> getAllCourses() {
        log.info("Получение всех курсов");
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourseById(Long id) {
        log.info("Получение курса с ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Курс с ID {} не найден", id);
                    return new CourseNotFoundException("Курс с ID " + id + " не найден");
                });
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        log.info("Создание нового курса: {}", courseDto.getName());
        Course course = courseMapper.toEntity(courseDto);
        course.setCreatedTime(LocalDateTime.now());
        course.setUpdatedTime(LocalDateTime.now());
        Course savedCourse = courseRepository.save(course);
        log.info("Курс '{}' успешно создан с ID: {}", savedCourse.getName(), savedCourse.getId());
        return courseMapper.toDto(savedCourse);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        log.info("Обновление курса с ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Курс с ID {} не найден для обновления", id);
                    return new CourseNotFoundException("Курс с ID " + id + " не найден");
                });

        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setUpdatedTime(LocalDateTime.now());

        Course updatedCourse = courseRepository.save(course);
        log.info("Курс с ID {} успешно обновлен", id);
        return courseMapper.toDto(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Удаление курса с ID: {}", id);
        if (!courseRepository.existsById(id)) {
            log.error("Курс с ID {} не найден для удаления", id);
            throw new CourseNotFoundException("Курс с ID " + id + " не найден");
        }
        courseRepository.deleteById(id);
        log.info("Курс с ID {} успешно удален", id);
    }
}
