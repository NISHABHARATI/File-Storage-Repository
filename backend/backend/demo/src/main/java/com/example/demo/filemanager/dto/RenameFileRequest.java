package com.example.demo.filemanager.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RenameFileRequest {
    // Getters and Setters
    private String oldName;
    private String newName;

}
