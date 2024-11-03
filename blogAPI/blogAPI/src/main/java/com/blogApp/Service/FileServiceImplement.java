package com.blogApp.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImplement implements FileService{
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //to get the file name
        String fileName=file.getOriginalFilename();

        //to get the file path
        String filePath=path+ File.separator +fileName;

        //object of the file
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //copy the file path and if file path already exists then replace it by the new file path
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
       String filePath=path+File.separator+filename;

        return new FileInputStream(filePath);
    }
}
