package com.chat.messenger.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @PostMapping("/upload")
    public String uploadString(@RequestParam("file") MultipartFile file) {
        // Setting up the path of the file
        String filePath = System.getProperty("user.dir") + "/Uploads" + File.separator + file.getOriginalFilename();
        String fileUploadStatus;
        // Try block to check for exception
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());
            fout.close();
            fileUploadStatus = "File Uploaded Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            fileUploadStatus = "Error in uploading file" + e;
        }
        return fileUploadStatus;
    }

    @GetMapping("/getFiles")
    public String[] getFiles() {
        // Getting the path of the directory
        String folderPath = System.getProperty("user.dir") + "/Uploads";

        // Creating instance of File

        File directory = new File(folderPath);
        String[] filenames = directory.list();// returns all files and dirs name inside this directory as represented by
                                              // the pathname
        return filenames;

    }

    @GetMapping("/download/{path:.+}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity downloadFile(@PathVariable("path") String fileName) throws FileNotFoundException {
        // Checking whether file exists or not logic

        String fileUploadFolderPath = System.getProperty("user.dir") + "/Uploads";
        String[] allFileNames = this.getFiles();
        boolean contains = Arrays.asList(allFileNames).contains(fileName);
        if (!contains) {
            return new ResponseEntity("File Not Found", HttpStatus.NOT_FOUND);
        }
        // Setting up the file path
        String filePath = fileUploadFolderPath + File.separator + fileName;
        // Creating a new file instance
        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // Creating new instance of HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        // Setting up values for content type and header

        String contentType = "application/octet-stream";// if an attempt made to download a file with unknown format it
                                                        // will be recognized as octet stream file by system.
        String headerValue = "attachment;filename =\"" + resource.getFilename() + "\"";// Content disposition response
                                                                                       // header as an attachment
                                                                                       // indicates that the content is
                                                                                       // downloadable.
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
    }
}
