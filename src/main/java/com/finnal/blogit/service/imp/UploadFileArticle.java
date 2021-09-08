package com.finnal.blogit.service.imp;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.service.inter.UploadFileService;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class UploadFileArticle implements UploadFileService {

    private final String FOLDER = "fileArticle";

    @Override
    public String getUrl(Long id, String name) {
        return Constant.FIREBASE_URL + Constant.BUCKET_NAME + "/" + getPathFolder(id) + "/" + name;
    }

    @Override
    public String save(Long id, MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String name = file.getOriginalFilename();
        String pathFolder = getPathFolder(id) + "/" + file.getOriginalFilename();
        bucket.create(pathFolder, file.getBytes(), file.getContentType());
        return name;
    }

    @Override
    public void delete(Long id, String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        if (!Strings.isEmpty(name)) {
            Blob blob = bucket.get(getPathFolder(id) + "/" + name);
            if (blob != null) {
                blob.delete();
            }
        }


    }

    @Override
    public void deleteAllById(Long id) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Page<Blob> blobPage = storage.list(Constant.BUCKET_NAME, Storage.BlobListOption.prefix(FOLDER + "/" + id + "/"));
        List<BlobId> blobIdList = new LinkedList<>();
        for (Blob blob : blobPage.iterateAll()) {
            blobIdList.add(blob.getBlobId());
        }
        if(blobIdList.size() > 0){
            storage.delete(blobIdList);
        }
    }


    private String getPathFolder(Long id) {
        return FOLDER + "/" + id;
    }
}
