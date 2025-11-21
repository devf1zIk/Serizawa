package kz.bitlab.devf1zIk.repository;

import kz.bitlab.devf1zIk.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
