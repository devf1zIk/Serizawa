package kz.bitlab.devf1zIk.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kz.bitlab.devf1zIk.dto.FileDto;
import kz.bitlab.devf1zIk.entity.UploadFile;
import kz.bitlab.devf1zIk.mapper.FileMapper;
import kz.bitlab.devf1zIk.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Value("${minio.bucket}")
    private String bucket;

    public String upload(MultipartFile file) {
        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setFileSize(file.getSize());
            uploadFile.setMimeType(file.getContentType());
            uploadFile.setInitialName(file.getOriginalFilename());
            uploadFile.setCreatedAt(LocalDateTime.now());

            uploadFile = fileRepository.save(uploadFile);
            if (uploadFile.getId() != null){

                String fileName = DigestUtils.sha1Hex(uploadFile.getId() + "My_File");
                uploadFile.setFileName(fileName);

                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(fileName)
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            };
            fileRepository.save(uploadFile);
            return "File uploaded successfully";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Some error on file upload";
    }

    public ByteArrayResource download(String fileName) {
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .build();
            InputStream inputStream = minioClient.getObject(getObjectArgs);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            return new ByteArrayResource(bytes);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<FileDto> getFileList(){
        return fileMapper.toDtoList(fileRepository.findAll());
    }
}
