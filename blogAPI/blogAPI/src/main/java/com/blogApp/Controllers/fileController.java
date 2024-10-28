package com.blogApp.Controllers;
import com.blogApp.Service.fileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class fileController {

  private final fileService fileServices;
  @Value("${project.poster}")
  private String path;

  public fileController(fileService fileService){
    this.fileServices = fileService;
  }

  @PostMapping("/upload/")
  public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
    String uploadedFileName = fileServices.uploadFile(path, file);
    return ResponseEntity.ok("File Uploaded: " + uploadedFileName);
  }

  @GetMapping("/{fileName}")
  public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException{
    InputStream resourceFile = fileServices.getResourceFile(path, fileName);
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    StreamUtils.copy(resourceFile,response.getOutputStream());
  }
}
