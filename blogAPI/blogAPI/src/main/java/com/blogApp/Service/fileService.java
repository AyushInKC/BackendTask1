package com.blogApp.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface fileService {
   String uploadFile(String path, MultipartFile file) throws IOException;

   InputStream getResourceFile(String path,String filename) throws FileNotFoundException;
}
