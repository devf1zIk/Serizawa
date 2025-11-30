package kz.bitlab.devf1zIk.mapper;

import kz.bitlab.devf1zIk.dto.FileDto;
import kz.bitlab.devf1zIk.entity.UploadFile;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    List<FileDto> toDtoList(List<UploadFile> uploadFiles);

    FileDto toDto(UploadFile uploadFile);

    UploadFile toEntity(FileDto fileDto);
}
