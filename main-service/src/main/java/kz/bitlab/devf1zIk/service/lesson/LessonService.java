package kz.bitlab.devf1zIk.service.lesson;

import kz.bitlab.devf1zIk.dto.LessonDto;
import java.util.List;

public interface LessonService {

    List<LessonDto> getAllLessons();

    LessonDto getLessonById(Long id);

    LessonDto createLesson(LessonDto lessonDto);

    LessonDto updateLesson(Long id, LessonDto lessonDto);

    void deleteLesson(Long id);
}
