package kz.bitlab.devf1zIk.mapper;

import javax.annotation.processing.Generated;
import kz.bitlab.devf1zIk.dto.CourseDto;
import kz.bitlab.devf1zIk.entity.Course;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-25T23:56:56+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDto courseDto = new CourseDto();

        courseDto.setId( course.getId() );
        courseDto.setName( course.getName() );
        courseDto.setDescription( course.getDescription() );
        courseDto.setCreatedTime( course.getCreatedTime() );
        courseDto.setUpdatedTime( course.getUpdatedTime() );

        return courseDto;
    }

    @Override
    public Course toEntity(CourseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( dto.getId() );
        course.name( dto.getName() );
        course.description( dto.getDescription() );
        course.createdTime( dto.getCreatedTime() );
        course.updatedTime( dto.getUpdatedTime() );

        return course.build();
    }
}
