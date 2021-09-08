package com.finnal.blogit.service.inter;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    String getUrl(Long id, String name);

    String save(Long id, MultipartFile file) throws IOException;

    void delete(Long id, String name) throws IOException;

    void deleteAllById(Long id) throws IOException;
}
