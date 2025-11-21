package kz.bitlab.devf1zIk.mapper;

import kz.bitlab.devf1zIk.dto.ChapterDto;
import kz.bitlab.devf1zIk.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    @Mapping(source = "course.id", target = "courseId")
    ChapterDto toDto(Chapter chapter);

    @Mapping(source = "courseId", target = "course.id")
    Chapter toEntity(ChapterDto dto);
}
