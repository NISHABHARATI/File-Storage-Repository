package com.example.demo.filemanager.service;

import com.example.demo.filemanager.entity.FileData;
import com.example.demo.filemanager.repository.FileRepository;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

//    public FileInfo uploadFile(Long userId, MultipartFile file, Long parentFolderId) throws IOException {
//        FileData fileData = new FileData();
//        fileData.setUserId(userId);
//        fileData.setParentFolderId(parentFolderId);
//        fileData.setFileName(file.getOriginalFilename());
//        fileData.setFolder(false);
//        fileData.setFile(true);
//        fileData.setBlobData(file.getBytes());
//        fileData.setCreatedAt(LocalDateTime.now());
//
//        fileRepository.save(fileData);
//        return null;
//    }

    public List<FileData> getFilesAndFolders(Long userId, Long parentFolderId) {
        return fileRepository.findByUserIdAndParentFolderId(userId, parentFolderId);
    }

    public FileData getFile(Long userId, String fileName) {
        return fileRepository.findByUserIdAndFileName(userId, fileName);
    }




    public String uploadFile(Long userId, MultipartFile file, Long parentFolderId) throws IOException {
        FileData fileData = new FileData();
        fileData.setUserId(userId);
        fileData.setParentFolderId(parentFolderId);
        fileData.setFileName(file.getOriginalFilename());
        fileData.setFolder(false);
        fileData.setFile(true);
        fileData.setBlobData(file.getBytes());
        fileData.setCreatedAt(LocalDateTime.now());

        // Save the file data to the repository
        fileRepository.save(fileData);

        // Return the filename
        return fileData.getFileName();
    }

    public void createFolder(Long userId, String folderName, Long parentFolderId) {
        FileData folder = new FileData();

        // Populate folder properties
        folder.setUserId(userId);
        folder.setFileName(folderName);  // Folder name
        folder.setParentFolderId(parentFolderId);  // Parent folder (or root)
        folder.setFolder(true);  // This is a folder
        folder.setFile(false);  // Not a file
        folder.setBlobData(null);  // No blob data for folders

        // Save folder data in the repository
        fileRepository.save(folder);
    }
//    public void createFolder(Long userId, String folderName, Long parentFolderId) {
//        FileData folderData = new FileData();
//        folderData.setUserId(userId);
//        folderData.setParentFolderId(parentFolderId);
//        folderData.setFileName(folderName);
//        folderData.setFolder(true);
//        folderData.setFile(false);
//        folderData.setCreatedAt(LocalDateTime.now());
//
//        fileRepository.save(folderData);
//    }
}
