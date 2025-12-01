package kz.bitlab.devf1zIk.repository;

import kz.bitlab.devf1zIk.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<UploadFile,Long> {
}
