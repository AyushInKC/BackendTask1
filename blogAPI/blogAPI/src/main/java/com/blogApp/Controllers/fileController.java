package com.blogApp.Controllers;

import com.blogApp.Service.fileService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class fileController {
  private final fileService fileServices;
  public fileController(fileService fileService){
      this.fileServices=fileService;
  }
  @Value("${project.poster}")
      private String path;
  @PostMapping("/upload/")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
//      fileServices.uploadFile(path,file);
      String uploadedFileName= fileServices.uploadFile(path,file);
      return ResponseEntity.ok("File Uploaded :"+uploadedFileName);
  }

  @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName , HttpServletResponse response) throws FileNotFoundException {
      InputStream resourceFile=fileServices.getResourceFile(path,fileName);
      response.setContentType(MediaType.IMAGE_PNG_VALUE);


  }

}
