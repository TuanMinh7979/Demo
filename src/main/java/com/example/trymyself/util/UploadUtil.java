package com.example.trymyself.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component

public class UploadUtil {


    public File handelUploadFile(MultipartFile uploadFile) {
        String dir = System.getProperty("user.dir");
        String folderPath = dir + "\\src\\main\\webapp\\resource\\osm";
        File myUploadFolder = new File(folderPath);

//        //kiem tra thu muc co ton tai neu khong thi tao moi
//        if (myUploadFolder.exists()) {
//            System.out.println("tao thu muc moi ");
//            //tao thu muc moi
//            myUploadFolder.mkdir();
//        }

        File savedFile = null;
        try {
            savedFile = new File(myUploadFolder, uploadFile.getOriginalFilename());
            uploadFile.transferTo(savedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Save file successfully!");
        return savedFile;
    }

//    public static void main(String[] args) {
//
//        String dir = System.getProperty("user.dir");
//        String folderPath = dir + "\\src\\main\\webapp\\resource\\osm";
//        System.out.println(folderPath);
//    }
}
