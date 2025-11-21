package kz.bitlab.devf1zIk.service.chapter;

import kz.bitlab.devf1zIk.dto.ChapterDto;
import java.util.List;

public interface ChapterService {

    List<ChapterDto> getAllChapters();

    ChapterDto getChapterById(Long id);

    ChapterDto createChapter(ChapterDto chapterDto);

    ChapterDto updateChapter(Long id, ChapterDto chapterDto);

    void deleteChapter(Long id);
}