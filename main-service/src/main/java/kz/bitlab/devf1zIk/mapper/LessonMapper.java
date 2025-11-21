package kz.bitlab.devf1zIk.mapper;

import kz.bitlab.devf1zIk.dto.LessonDto;
import kz.bitlab.devf1zIk.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(source = "chapter.id", target = "chapterId")
    LessonDto toDto(Lesson lesson);

    @Mapping(source = "chapterId", target = "chapter.id")
    Lesson toEntity(LessonDto dto);
}
