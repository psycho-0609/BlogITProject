package com.ckfinder.demo.untils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadFileUtils {


    public void writeOrUpdate(Long id, byte[] bytes, String name) throws IOException {
        String uploadDir ="./video/"+id;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(uploadDir+"/"+name);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
    public  void deleteFile(String dirFile) {

        File file = new File(dirFile);
        if(file.exists()) {
            deleteProcess(file);
        }else{
            return;
        }
    }
    public void deleteProcess(File file) {
        if (file.isDirectory()) {
            // liet ke tat ca thu muc va file
            String[] files = file.list();
            for (String child : files) {
                File childDir = new File(file, child);
                if (childDir.isDirectory()) {
                    // neu childDir la thu muc thi goi lai phuong thuc deleteDir()
                    deleteProcess(childDir);
                } else {
                    // neu childDir la file thi xoa
                    childDir.delete();
                    System.out.println("File bi da bi xoa "
                            + childDir.getAbsolutePath());
                }
            }
            // Check lai va xoa thu muc cha
            if (file.list().length == 0) {
                file.delete();
                System.out.println("File bi da bi xoa " + file.getAbsolutePath());
            }

        } else {
            // neu file la file thi xoa
            file.delete();
            System.out.println("File bi da bi xoa " + file.getAbsolutePath());
        }
    }
}
