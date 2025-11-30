package kz.bitlab.devf1zIk.api;

import kz.bitlab.devf1zIk.dto.FileDto;
import kz.bitlab.devf1zIk.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/file")
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload")
    public String upload(@RequestParam(name = "file") MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping(value = "/download")
    private ResponseEntity<ByteArrayResource> download(@PathVariable(name = "file") String fileName) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileService.download(fileName));
    }

    @GetMapping(value = "/list")
    public List<FileDto> getFiles() {
        return fileService.getFileList();
    }
}
