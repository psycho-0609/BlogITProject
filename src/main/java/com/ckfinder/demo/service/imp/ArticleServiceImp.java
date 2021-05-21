package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.dto.ArticleDTO;
import com.ckfinder.demo.entity.ArticleEntity;
import com.ckfinder.demo.entity.UserAccountEntity;
import com.ckfinder.demo.repository.ArticleRepository;
import com.ckfinder.demo.repository.UserAccountRepository;
import com.ckfinder.demo.service.IArticleService;
import com.ckfinder.demo.untils.UploadFileUtils;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImp implements IArticleService {

    @Autowired
    private  ArticleRepository articleRepository;
    @Autowired
    private UploadFileUtils fileUtils;
    @Autowired
    private UserAccountRepository accountRepository;

    private String dir = "./video/";

    @Override
    public List<ArticleEntity> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public ArticleEntity insertArticle(ArticleDTO articleEntity) {
        ArticleEntity entity = new ArticleEntity();
        entity.setContent(articleEntity.getContent());
        entity.setTitle(articleEntity.getTitle());
        entity.setImage(articleEntity.getFile());
        entity.setIsNew(true);
        entity.setIsPublic(false);
//        UserAccountEntity userAccountEntity = accountRepository.findByEmail(UserInfor.getPrincipal().getUsername());
//        entity.setUserAccount(userAccountEntity);
        ArticleEntity articleSaved = articleRepository.save(entity);
        if(!articleEntity.getBase64().equals("")){
            byte[] decodeBase64 = Base64.getDecoder().decode(articleEntity.getBase64().getBytes());
            try {
                fileUtils.writeOrUpdate(articleSaved.getId(),decodeBase64,articleSaved.getImage());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return articleSaved;
    }

    @Override
    public ArticleEntity findOne(Long id) {
        Optional<ArticleEntity> entity = articleRepository.findById(id);
        if (entity.isPresent()){
            return entity.get();
        }
        return null;
    }

    @Override
    public ArticleEntity update(ArticleDTO articleDTO) {
        Optional<ArticleEntity> entity = articleRepository.findById(articleDTO.getId());
        if(!entity.isPresent()){

        }
        ArticleEntity currentEntity = entity.get();
        currentEntity.setTitle(articleDTO.getTitle());
        currentEntity.setContent(articleDTO.getContent());
        if(!articleDTO.getFile().equals("")){
            currentEntity.setImage(articleDTO.getFile());
            byte[] decodeBase64 = Base64.getDecoder().decode(articleDTO.getBase64().getBytes());
            try {
                fileUtils.writeOrUpdate(currentEntity.getId(),decodeBase64,currentEntity.getImage());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        currentEntity = articleRepository.save(currentEntity);

        return currentEntity;
    }

    @Override
    public Optional<ArticleEntity> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
        fileUtils.deleteFile(dir + id);
    }

    @Override
    public ArticleEntity changeStatus(ArticleDTO dto){
        ArticleEntity entity = articleRepository.findById(dto.getId()).get();
        entity.setIsPublic(dto.getIsPublic());
        entity.setIsNew(false);
        return articleRepository.save(entity);
    }


}
