package com.example.demo.filemanager.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "files",schema ="TRAINING_NISHA")
public class FileData {

    @Setter
    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id", unique = true)
    private Long tableId;

    @Setter
    @Getter
    @Column(name = "parent_folder_id")
    private Long parentFolderId = -1L;

    @Column(name = "is_folder", nullable = false)
    private Boolean isFolder;

    @Column(name = "is_file", nullable = false)
    private Boolean isFile;

    @Setter
    @Getter
    @Lob
    @Column(name = "blob_data")
    private byte[] blobData;

    @Setter
    @Getter
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Setter
    @Getter
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Boolean getFolder() {
        return isFolder;
    }

    public void setFolder(Boolean folder) {
        isFolder = folder;
    }

    public Boolean getFile() {
        return isFile;
    }

    public void setFile(Boolean file) {
        isFile = file;
    }

    public boolean exists() {
        return this.userId != null && this.fileName != null && !this.fileName.isEmpty();
    }

    public String getPath() {
        return this.tableId != null ? this.tableId.toString() : null; // or return fileName or any unique identifier
    }



}

