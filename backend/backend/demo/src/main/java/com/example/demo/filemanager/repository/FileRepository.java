package com.example.demo.filemanager.repository;

import com.example.demo.filemanager.entity.FileData;
import org.apache.http.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileData, Long> {
    // Custom queries if needed
    List<FileData> findByUserIdAndParentFolderId(Long userId, Long parentFolderId);
    FileData findByUserIdAndFileName(Long userId, String fileName);
}
