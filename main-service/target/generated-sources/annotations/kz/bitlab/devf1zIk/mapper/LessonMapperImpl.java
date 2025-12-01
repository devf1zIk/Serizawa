package kz.bitlab.devf1zIk.mapper;

import javax.annotation.processing.Generated;
import kz.bitlab.devf1zIk.dto.LessonDto;
import kz.bitlab.devf1zIk.entity.Chapter;
import kz.bitlab.devf1zIk.entity.Lesson;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T19:20:20+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class LessonMapperImpl implements LessonMapper {

    @Override
    public LessonDto toDto(Lesson lesson) {
        if ( lesson == null ) {
            return null;
        }

        LessonDto lessonDto = new LessonDto();

        lessonDto.setChapterId( lessonChapterId( lesson ) );
        lessonDto.setId( lesson.getId() );
        lessonDto.setName( lesson.getName() );
        lessonDto.setDescription( lesson.getDescription() );
        lessonDto.setContent( lesson.getContent() );
        lessonDto.setOrder( lesson.getOrder() );
        lessonDto.setCreatedTime( lesson.getCreatedTime() );
        lessonDto.setUpdatedTime( lesson.getUpdatedTime() );

        return lessonDto;
    }

    @Override
    public Lesson toEntity(LessonDto dto) {
        if ( dto == null ) {
            return null;
        }

        Lesson.LessonBuilder lesson = Lesson.builder();

        lesson.chapter( lessonDtoToChapter( dto ) );
        lesson.id( dto.getId() );
        lesson.name( dto.getName() );
        lesson.description( dto.getDescription() );
        lesson.content( dto.getContent() );
        if ( dto.getOrder() != null ) {
            lesson.order( dto.getOrder() );
        }
        lesson.createdTime( dto.getCreatedTime() );
        lesson.updatedTime( dto.getUpdatedTime() );

        return lesson.build();
    }

    private Long lessonChapterId(Lesson lesson) {
        if ( lesson == null ) {
            return null;
        }
        Chapter chapter = lesson.getChapter();
        if ( chapter == null ) {
            return null;
        }
        Long id = chapter.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Chapter lessonDtoToChapter(LessonDto lessonDto) {
        if ( lessonDto == null ) {
            return null;
        }

        Chapter.ChapterBuilder chapter = Chapter.builder();

        chapter.id( lessonDto.getChapterId() );

        return chapter.build();
    }
}
