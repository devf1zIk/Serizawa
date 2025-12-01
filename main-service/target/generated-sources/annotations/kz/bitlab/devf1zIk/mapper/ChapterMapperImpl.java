package kz.bitlab.devf1zIk.mapper;

import javax.annotation.processing.Generated;
import kz.bitlab.devf1zIk.dto.ChapterDto;
import kz.bitlab.devf1zIk.entity.Chapter;
import kz.bitlab.devf1zIk.entity.Course;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T19:20:20+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ChapterMapperImpl implements ChapterMapper {

    @Override
    public ChapterDto toDto(Chapter chapter) {
        if ( chapter == null ) {
            return null;
        }

        ChapterDto chapterDto = new ChapterDto();

        chapterDto.setCourseId( chapterCourseId( chapter ) );
        chapterDto.setId( chapter.getId() );
        chapterDto.setName( chapter.getName() );
        chapterDto.setDescription( chapter.getDescription() );
        chapterDto.setOrder( chapter.getOrder() );
        chapterDto.setCreatedTime( chapter.getCreatedTime() );
        chapterDto.setUpdatedTime( chapter.getUpdatedTime() );

        return chapterDto;
    }

    @Override
    public Chapter toEntity(ChapterDto dto) {
        if ( dto == null ) {
            return null;
        }

        Chapter.ChapterBuilder chapter = Chapter.builder();

        chapter.course( chapterDtoToCourse( dto ) );
        chapter.id( dto.getId() );
        chapter.name( dto.getName() );
        chapter.description( dto.getDescription() );
        if ( dto.getOrder() != null ) {
            chapter.order( dto.getOrder() );
        }
        chapter.createdTime( dto.getCreatedTime() );
        chapter.updatedTime( dto.getUpdatedTime() );

        return chapter.build();
    }

    private Long chapterCourseId(Chapter chapter) {
        if ( chapter == null ) {
            return null;
        }
        Course course = chapter.getCourse();
        if ( course == null ) {
            return null;
        }
        Long id = course.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Course chapterDtoToCourse(ChapterDto chapterDto) {
        if ( chapterDto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( chapterDto.getCourseId() );

        return course.build();
    }
}
