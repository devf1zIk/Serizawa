package kz.bitlab.devf1zIk.service.course;

import kz.bitlab.devf1zIk.dto.CourseDto;
import java.util.List;

public interface CourseService {

    List<CourseDto> getAllCourses();

    CourseDto getCourseById(Long id);

    CourseDto createCourse(CourseDto courseDto);

    CourseDto updateCourse(Long id, CourseDto courseDto);

    void deleteCourse(Long id);
}