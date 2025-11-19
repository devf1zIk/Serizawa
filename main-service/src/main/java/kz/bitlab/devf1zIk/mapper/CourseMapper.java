package kz.bitlab.devf1zIk.mapper;

import kz.bitlab.devf1zIk.dto.CourseDto;
import kz.bitlab.devf1zIk.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Course course);

    Course toEntity(CourseDto dto);
}
