package com.finnal.blogit.service.imp;

import com.finnal.blogit.constant.Constant;
import com.finnal.blogit.dto.response.*;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.dto.request.ArticleRequest;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticleNew;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import com.finnal.blogit.repository.ArticleRepository;
import com.finnal.blogit.repository.TopicRepository;
import com.finnal.blogit.repository.UserAccountRepository;
import com.finnal.blogit.service.inter.IArticleService;
import com.finnal.blogit.untils.UploadFileUtils;
import com.finnal.blogit.user.UserInfor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UploadFileUtils fileUtils;

    @Autowired
    private UserAccountRepository accountRepository;

    @Autowired
    private TopicRepository topicRepository;

    private final String[] LIST_MONTH = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "September", "October", "November", "December"};

    @Override
    public List<ArticleEntity> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<CustomArticleDTO> findAllApi() {
        List<ArticleEntity> lists = articleRepository.findAll();
        List<CustomArticleDTO> dtoList = new ArrayList<>();
        for (ArticleEntity item : lists) {
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
            articleDTO.setPublished(item.getPublished());
            articleDTO.setNews(item.getNews());
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
        entity.setNews(ArticleNew.ENABLE);
        entity.setStatus(ArticleStatus.fromValue(articleEntity.getStatus()));
        entity.setPublished(ArticlePublished.DISABLE);
        entity.setCountView(0L);
        entity.setShortDescription(articleEntity.getShortDescription());
        entity.setTopic(topicRepository.findById(articleEntity.getTopicId()).get());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setModifiedDate(LocalDateTime.now());
        UserAccountEntity userAccountEntity = accountRepository.findByEmail(UserInfor.getPrincipal().getUsername()).get();
        entity.setUserAccount(userAccountEntity);
        ArticleEntity articleSaved = articleRepository.save(entity);
        if (articleEntity.getBase64() != null && articleEntity.getFile() != null) {
            if (!articleEntity.getFile().equals("") && !articleEntity.getBase64().equals("")) {
                byte[] decodeBase64 = Base64.getDecoder().decode(articleEntity.getBase64().getBytes());
                try {
                    fileUtils.writeOrUpdate(articleSaved.getId(), decodeBase64, articleSaved.getImage(), Constant.DIR_IMG_BACK_ARTICLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return articleSaved;
    }

    @Override
    public ArticleEntity findOne(Long id) {
        Optional<ArticleEntity> entity = articleRepository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        return null;
    }

    @Override
    @Transactional
    public ArticleEntity update(ArticleRequest articleRequest) {
        String img;
        ArticleEntity currentEntity = articleRepository.findById(articleRequest.getId()).get();
        img = currentEntity.getImage();
        currentEntity.setTitle(articleRequest.getTitle());
        currentEntity.setContent(articleRequest.getContent());
        currentEntity.setShortDescription(articleRequest.getShortDescription());
        currentEntity.setTopic(topicRepository.findById(articleRequest.getTopicId()).get());
        currentEntity.setStatus(ArticleStatus.fromValue(articleRequest.getStatus()));
        currentEntity.setModifiedDate(LocalDateTime.now());
        if (articleRequest.getBase64() != null && articleRequest.getFile() != null) {
            if (!articleRequest.getFile().equals("") && !articleRequest.getBase64().equals("")) {
                currentEntity.setImage(articleRequest.getFile());
                byte[] decodeBase64 = Base64.getDecoder().decode(articleRequest.getBase64().getBytes());
                try {
                    fileUtils.writeOrUpdate(currentEntity.getId(), decodeBase64, articleRequest.getFile(), Constant.DIR_IMG_BACK_ARTICLE);
                    if (img != null && Strings.isEmpty(img)) {
                        String deleteDir = Constant.DIR_IMG_BACK_ARTICLE + currentEntity.getId() + "/" + img;
                        fileUtils.deleteFile(deleteDir);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        fileUtils.deleteFile(Constant.DIR_IMG_BACK_ARTICLE + id);
    }


    @Override
    public void plusCountView(ArticleEntity entity) {
        entity.setCountView(entity.getCountView() + 1);
        articleRepository.save(entity);
    }

    @Override
    public ArticleEntity save(ArticleEntity articleEntity) {
        return articleRepository.save(articleEntity);
    }

    @Override
    public List<CustomArticleDTO> findAllByPublishedStatusAndAccount(ArticlePublished published, Long id, ArticleStatus status) {
        return articleRepository.findAllByPublishedStatusAndUserAccountId(published, id, status);
    }

    @Override
    public List<CustomArticleDTO> findAllByAccountId(Long id) {

        return articleRepository.findAllByAccountId(id);
    }

    @Override
    public List<CustomArticleDTO> findAllForSearch(ArticlePublished published, Long id, ArticleStatus status, String title) {
        return articleRepository.findAllForSearch(published, id, status, title);
    }

    @Override
    public List<CustomArticleDTO> findByPublishedAndStatus(ArticlePublished published, ArticleStatus status) {
        return articleRepository.findAllByPublishedAndStatus(published, status);
    }

    @Override
    public List<CustomArticleDTO> findAllByTopicId(Integer id) {
        return articleRepository.findByTopicId(ArticlePublished.ENABLE, ArticleStatus.PUBLIC, id);
    }

    @Override
    public Long totalCountView(List<Long> ids) {
        return articleRepository.totalCountView(ids);
    }

    @Override
    public List<ArticleEntity> findByPrioritize() {
        return articleRepository.findAllByPrioritize();
    }

    @Override
    public List<ArticleEntity> getForPopular() {
        return articleRepository.findForPopular();
    }

    @Override
    public List<CustomArticleDTO> findByStatus(ArticleStatus status) {
        return articleRepository.findByStatus(status);
    }

    @Override
    public Optional<ArticleCustomDTO> getById(Long id) {
        return articleRepository.getById(id);
    }

    @Override
    public Optional<ArticleEntity> findByPrioritize(Integer number) {
        return articleRepository.findByPrioritize(number);
    }

    @Override
    public List<CustomArticleDTO> findByPublishedAndStatusForWeb(ArticlePublished published, ArticleStatus status) {
        return articleRepository.findAllByPublishedAndStatusForWeb(published, status);
    }

    @Override
    public List<ArticleEntity> getAllOderByFavCount(ArticlePublished published, ArticleStatus status) {
        return articleRepository.getAllOderByFavCount(published, status);
    }

    @Override
    public Long countAllByStatusAndPublished() {
        return articleRepository.countAllByStatusAndPublished();
    }

    @Override
    public List<StatisticCustomDTO> getForStatistic() {
        Integer currentMonth = LocalDateTime.now().getMonthValue();
        List<Integer> listMonth = getListMonth(currentMonth);
        List<StatisticCustomDTO> statistics = new ArrayList<>();
        for (Integer month : listMonth) {
            StatisticCustomDTO statistic = new StatisticCustomDTO();
            statistic.setMonth(LIST_MONTH[month - 1].substring(0, 3));
            statistic.setAmount(articleRepository.countByMonth(month));
            statistics.add(statistic);
        }
        return statistics;
    }

    @Override
    public List<StatisticPieChartCustom> getStatisticPercent() {
        List<CustomTopicDTO> topics = topicRepository.getAll();
        List<StatisticPieChartCustom> list = new ArrayList<>();
        int month = LocalDateTime.now().getMonthValue();
        Long totalArticle = articleRepository.countAllByStatusAndPublished();
        String rgb;
        for (CustomTopicDTO topic : topics) {
            rgb = "rgb(" + randomRGB() + ", " + randomRGB() + ", " + randomRGB() + ")";
            StatisticPieChartCustom statistic = new StatisticPieChartCustom();
            statistic.setPercent(Float.valueOf(articleRepository.countAllByTopicAndMonth(month, topic.getId())) / totalArticle * 100);
            statistic.setTopic(topic);
            statistic.setColor(rgb);
            list.add(statistic);
        }
        return list;
    }

    @Override
    public boolean isExistedById(Long id) {
        return articleRepository.existsById(id);
    }

    private List<Integer> getListMonth(Integer currentMonth) {
        List<Integer> list = new ArrayList<>();
        for (int i = 6; i > 0; i--) {
            if (currentMonth - i >= 0) {
                list.add(currentMonth - i + 1);
            } else {
                list.add(currentMonth - i + 12 + 1);
            }
        }
        return list;
    }

    private Integer randomRGB() {
        Random random = new Random();
        return random.nextInt((255) + 1);
    }


}
