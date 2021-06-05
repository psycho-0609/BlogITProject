package com.ckfinder.demo.service.imp;

import com.ckfinder.demo.dto.response.CustomArticleDTO;
import com.ckfinder.demo.dto.response.CustomUserAccount;
import com.ckfinder.demo.dto.response.CustomerUserDetailDTO;
import com.ckfinder.demo.entity.UserAccountEntity;
import com.ckfinder.demo.dto.request.ArticleRequest;
import com.ckfinder.demo.entity.ArticleEntity;
import com.ckfinder.demo.repository.ArticleRepository;
import com.ckfinder.demo.repository.TopicRepository;
import com.ckfinder.demo.repository.UserAccountRepository;
import com.ckfinder.demo.service.inter.IArticleService;
import com.ckfinder.demo.untils.UploadFileUtils;
import com.ckfinder.demo.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService implements IArticleService {

    @Autowired
    private  ArticleRepository articleRepository;
    @Autowired
    private UploadFileUtils fileUtils;
    @Autowired
    private UserAccountRepository accountRepository;

    @Autowired
    private TopicRepository topicRepository;

    private String dir = "./video/";

    @Override
    public List<ArticleEntity> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<CustomArticleDTO> findAllApi() {
        List<ArticleEntity> lists =  articleRepository.findAll();
        List<CustomArticleDTO> dtoList = new ArrayList<>();
        for(ArticleEntity item:lists){
            CustomArticleDTO articleDTO = new CustomArticleDTO();
            CustomerUserDetailDTO detailDTO = new CustomerUserDetailDTO();
            detailDTO.setId(item.getUserAccount().getUserDetailEntity().getId());
            detailDTO.setLastName(item.getUserAccount().getUserDetailEntity().getLastName());
            detailDTO.setFirstName(item.getUserAccount().getUserDetailEntity().getLastName());
            detailDTO.setThumbnail(item.getUserAccount().getUserDetailEntity().getThumbnail());
            CustomUserAccount userAccount = new CustomUserAccount();
            userAccount.setUserDetail(detailDTO);
            userAccount.setId(item.getUserAccount().getId());
            userAccount.setEmail(item.getUserAccount().getEmail());
            articleDTO.setUserAccount(userAccount);
            articleDTO.setId(item.getId());
            articleDTO.setImagePath(item.getImagePath());
            articleDTO.setCountView(item.getCountView());
            articleDTO.setIsPublic(item.getIsPublic());
            articleDTO.setIsNew(item.getIsNew());
            articleDTO.setTitle(item.getTitle());
            articleDTO.setShortDescription(item.getShortDescription());
            dtoList.add(articleDTO);
        }
        return dtoList;
    }

    @Override
    @Transactional
    public ArticleEntity insertArticle(ArticleRequest articleEntity) {
        ArticleEntity entity = new ArticleEntity();
        entity.setContent(articleEntity.getContent());
        entity.setTitle(articleEntity.getTitle());
        entity.setImage(articleEntity.getFile());
        entity.setIsNew(true);
        entity.setIsPublic(false);
        entity.setCountView(0L);
        entity.setShortDescription(articleEntity.getShortDescription());
        entity.setTopic(topicRepository.findById(articleEntity.getTopicId()).get());
        UserAccountEntity userAccountEntity = accountRepository.findByEmail(UserInfor.getPrincipal().getUsername());
        entity.setUserAccount(userAccountEntity);
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
    @Transactional
    public ArticleEntity update(ArticleRequest articleRequest) {

        ArticleEntity currentEntity =  articleRepository.findById(articleRequest.getId()).get();
        currentEntity.setTitle(articleRequest.getTitle());
        currentEntity.setContent(articleRequest.getContent());
        currentEntity.setShortDescription(articleRequest.getShortDescription());
        currentEntity.setTopic(topicRepository.findById(articleRequest.getTopicId()).get());
        if(!articleRequest.getFile().equals("")){
            currentEntity.setImage(articleRequest.getFile());
            byte[] decodeBase64 = Base64.getDecoder().decode(articleRequest.getBase64().getBytes());
            try {
                fileUtils.writeOrUpdate(currentEntity.getId(),decodeBase64,articleRequest.getFile());
                String uploadDir = dir + currentEntity.getId() + "/" + currentEntity.getImage();
                fileUtils.deleteFile(uploadDir);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return articleRepository.save(currentEntity);
    }

    @Override
    public Optional<ArticleEntity> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
        fileUtils.deleteFile(dir + id);
    }


    @Override
    public void plusCountView(ArticleEntity entity) {
        entity.setCountView(entity.getCountView()+1);
        articleRepository.save(entity);
    }

    @Override
    public ArticleEntity save(ArticleEntity articleEntity) {
        return articleRepository.save(articleEntity);
    }


}
