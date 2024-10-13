package com.example.demo.filemanager.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name="employee", schema = "TRAINING_NISHA")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_meta_seq")
    @SequenceGenerator(name = "file_meta_seq", sequenceName = "file_meta_seq", allocationSize = 1)
    private Long id;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;

    @Column(name = "FILE_SIZE")
    private long filesize;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPLOADED_AT")
    private Date uploadedAt;

    @Column(name = "OWNER")
    private String owner;


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public long getFilesize() {
        return filesize;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getOwner() {
        return owner;
    }
}
