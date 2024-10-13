//package com.example.demo.filemanager.controller;
//
//import com.example.demo.filemanager.service.FileService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/files")
//public class FileController {
//
//    @Autowired
//    private FileService fileService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
//                                             @RequestParam(value = "parentFolderId", defaultValue = "-1") Long parentFolderId,
//                                             HttpServletRequest request) {
//        try {
//            HttpSession session = request.getSession();
//            Long userId = (Long) session.getAttribute("userId");  // Get user_id from session
//            System.out.println(userId);
//            if (userId == null) {
//                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
//            }
//
//            fileService.uploadFile(userId,file, parentFolderId);
//            return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/uploadFolder")
//    public ResponseEntity<String> uploadFolder(@RequestParam("folderName") String folderName,
//                                               @RequestParam(value = "parentFolderId", defaultValue = "-1") Long parentFolderId,
//                                               HttpServletRequest request) {
//        try {
//            HttpSession session = request.getSession();
//            Long userId = (Long) session.getAttribute("userId");  // Get user_id from session
//            System.out.println(userId);
//
//            if (userId == null) {
//                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
//            }
//
//            // Call service to handle folder creation
//            fileService.createFolder(userId, folderName, parentFolderId);
//            return new ResponseEntity<>("Folder created successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to create folder", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//}
//
////    @PostMapping("/create-folder")
////    public ResponseEntity<String> createFolder(@RequestParam("folderName") String folderName,
////                                               @RequestParam(value = "parentFolderId", defaultValue = "-1") Long parentFolderId,
////                                               HttpServletRequest request) {
////        HttpSession session = request.getSession();
////        Long userId = (Long) session.getAttribute("userId");
////
////        if (userId == null) {
////            return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
////        }
////
////        fileService.createFolder(userId, folderName, parentFolderId);
////        return new ResponseEntity<>("Folder created successfully", HttpStatus.CREATED);
////    }
//}


package com.example.demo.filemanager.controller;

import com.example.demo.filemanager.entity.FileData;
import com.example.demo.filemanager.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    @Autowired
    private FileService fileService;


//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
//                                             @RequestParam(value = "parentFolderId", defaultValue = "-1") Long parentFolderId,
//                                             @RequestHeader(value = "userId", required = false) Long headerUserId,  // Optional userId from header
//                                             HttpServletRequest request) {
//        try {
//            Long userId = getUserId(headerUserId, request);
//            if (userId == null) {
//                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
//            }
//
//            fileService.uploadFile(userId, file, parentFolderId);
//            return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
@GetMapping("/list")
public ResponseEntity<List<FileData>> listFilesAndFolders(
        @RequestParam(value = "parentId") Long parentId,
        @RequestParam(value = "userId") Long userId) {
    try {
        List<FileData> filesAndFolders = fileService.getFilesAndFolders(userId, parentId);
        return new ResponseEntity<>(filesAndFolders, HttpStatus.OK);
    } catch (Exception e) {
        e.printStackTrace();  // Log the exception for debugging
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file,
                                                          @RequestParam(value = "parentFolderId", defaultValue = "-1") Long parentFolderId,
                                                          @RequestHeader(value = "userId", required = false) Long headerUserId,  // Optional userId from header
                                                          HttpServletRequest request) {
        try {
            Long userId = getUserId(headerUserId, request);
            if (userId == null) {
                return new ResponseEntity<>(Map.of("message", "User not logged in"), HttpStatus.UNAUTHORIZED);
            }

            String fileName = fileService.uploadFile(userId, file, parentFolderId);  // Assuming uploadFile returns the file name
            return new ResponseEntity<>(Map.of("fileName", fileName), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Failed to upload file"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Object> downloadFile(@RequestHeader("userId") Long userId,
                                               @RequestHeader("fileName") String fileName,
                                               HttpServletRequest request) {
        try {
            // Validate the user (you can add more validations here if needed)
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // Fetch the file from your file storage service
            FileData file = fileService.getFile(userId, fileName);

            if (file == null || file.getBlobData() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Determine the file's content type
            String contentType = request.getServletContext().getMimeType(file.getFileName());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Create a byte array resource for the file data
            ByteArrayResource resource = new ByteArrayResource(file.getBlobData());

            // Return the file as a response with appropriate headers
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .contentLength(file.getBlobData().length)
                    .body(resource);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping("/download")
//    public ResponseEntity<byte[]> downloadFile(@RequestHeader("userId") Long userId,
//                                               @RequestHeader("fileName") String fileName) {
//    // validate user
//    if (userId == null) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        try {
//
//
//            // Fetch the file from your file storage service
//            FileData file = fileService.getFile(userId, fileName);
//
//            // Check if file exists
//            if (file == null || file.getBlobData() == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            // Determine the file's content type (You can set it according to your needs)
//            String contentType = "application/octet-stream"; // Default to binary
//
//            // Return the file as a response with appropriate headers
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(contentType))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
//                    .body(file.getBlobData()); // Return the file data directly
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    @PostMapping("/uploadFolder")
    public ResponseEntity<String> uploadFolder(@RequestParam("folderName") String folderName,
                                               @RequestParam(value = "parentFolderId", defaultValue = "-1") Long parentFolderId,
                                               @RequestHeader(value = "userId", required = false) Long headerUserId,  // Optional userId from header
                                               HttpServletRequest request) {
        try {
            Long userId = getUserId(headerUserId, request);
            if (userId == null) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }

            // Call service to handle folder creation
            fileService.createFolder(userId, folderName, parentFolderId);
            return new ResponseEntity<>("Folder created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create folder", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to determine userId from header or session
    private Long getUserId(Long headerUserId, HttpServletRequest request) {
        if (headerUserId != null) {
            System.out.println("User ID from Header: " + headerUserId);
            return headerUserId;
        }

        HttpSession session = request.getSession();
        Long sessionUserId = (Long) session.getAttribute("userId");
        System.out.println("User ID from Session: " + sessionUserId);

        return sessionUserId;
    }
}

