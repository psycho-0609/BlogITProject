package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.*;
import com.finnal.blogit.entity.TopicEntity;
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
import com.finnal.blogit.service.inter.UploadFileService;
import com.finnal.blogit.untils.UploadFileUtils;
import com.finnal.blogit.user.UserInfor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("uploadFileArticle")
    private UploadFileService uploadFileService;

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
    public ArticleEntity insertArticle(ArticleRequest request) throws IOException {
        ArticleEntity entity = new ArticleEntity();
        entity.setContent(request.getContent());
        entity.setTitle(request.getTitle());
        if (request.getImage() != null && !request.getImage().getOriginalFilename().equals("")) {
            entity.setImage(request.getImage().getOriginalFilename());
        }
        if (request.getVideo() != null && !request.getVideo().getOriginalFilename().equals("")) {
            entity.setVideoFile(request.getVideo().getOriginalFilename());
        }
        entity.setNews(ArticleNew.ENABLE);
        entity.setStatus(ArticleStatus.fromValue(request.getStatus()));
        entity.setPublished(ArticlePublished.DISABLE);
        entity.setCountView(0L);
        entity.setShortDescription(request.getShortDescription());
        entity.setTopic(new TopicEntity(request.getTopicId()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setModifiedDate(LocalDateTime.now());
        UserAccountEntity userAccountEntity = accountRepository.findByEmail(UserInfor.getPrincipal().getUsername()).get();
        entity.setUserAccount(userAccountEntity);
        ArticleEntity articleSaved = articleRepository.save(entity);
        if (request.getImage() != null && !Strings.isEmpty(request.getImage().getOriginalFilename())) {
            uploadFileService.save(entity.getId(), request.getImage());
        }
        if (request.getVideo() != null && !Strings.isEmpty(request.getVideo().getOriginalFilename())) {
            uploadFileService.save(entity.getId(), request.getVideo());
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
    public ArticleEntity update(ArticleRequest request) {
        String img;
        String video;
        ArticleEntity currentEntity = articleRepository.findById(request.getId()).get();
        img = currentEntity.getImage();
        video = currentEntity.getVideoFile();
        currentEntity.setTitle(request.getTitle());
        currentEntity.setContent(request.getContent());
        currentEntity.setShortDescription(request.getShortDescription());
        currentEntity.setTopic(new TopicEntity(request.getTopicId()));
        currentEntity.setStatus(ArticleStatus.fromValue(request.getStatus()));
        currentEntity.setModifiedDate(LocalDateTime.now());
        if (request.getVideo() != null && !request.getVideo().getOriginalFilename().equals("")) {
            currentEntity.setVideoFile(request.getVideo().getOriginalFilename());
            try {
                uploadFileService.save(currentEntity.getId(), request.getVideo());
                if (video != null && !Strings.isEmpty(video)) {
                    uploadFileService.delete(currentEntity.getId(), video);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (request.getImage() != null && !request.getImage().getOriginalFilename().equals("")) {
            currentEntity.setImage(request.getImage().getOriginalFilename());
            try {
                uploadFileService.save(currentEntity.getId(), request.getImage());
                if (img != null && !Strings.isEmpty(img)) {
                    uploadFileService.delete(currentEntity.getId(), img);
                }
            } catch (IOException e) {
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
    public void deleteArticle(Long id) throws IOException {
        articleRepository.deleteById(id);
        uploadFileService.deleteAllById(id);
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
    public List<CustomArticleDTO> getForPopular() {
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
        Integer currentYear = LocalDateTime.now().getYear();
        Map<Integer, Integer> mapMonth = getListMonth(currentMonth, currentYear);
        List<StatisticCustomDTO> statistics = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : mapMonth.entrySet()) {
            StatisticCustomDTO statistic = new StatisticCustomDTO();
            statistic.setMonth(LIST_MONTH[entry.getKey() - 1].substring(0, 3));
            statistic.setAmount(articleRepository.countByMonth(entry.getKey(), entry.getValue()));
            statistics.add(statistic);
        }
        return statistics;
    }

    @Override
    public List<StatisticPieChartCustom> getStatisticPercent() {
        List<CustomTopicDTO> topics = topicRepository.getAll();
        List<StatisticPieChartCustom> list = new ArrayList<>();
        int month = LocalDateTime.now().getMonthValue();
        Integer year = LocalDateTime.now().getYear();
        Long totalArticle = articleRepository.countByMonth(month, year);
        String rgb;
        for (CustomTopicDTO topic : topics) {
            rgb = "rgb(" + randomRGB() + ", " + randomRGB() + ", " + randomRGB() + ")";
            StatisticPieChartCustom statistic = new StatisticPieChartCustom();
            statistic.setPercent(Float.valueOf(articleRepository.countAllByTopicAndMonth(month, year, topic.getId())) / totalArticle * 100);
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

    @Override
    public List<StatisticAuthor> getStatisticAuthor() {
        return articleRepository.getStatisticAuthor();
    }

    @Override
    public List<ArticleEntity> findAllByTopicIdForRelease(Integer topicId, Long articleId) {
        return articleRepository.findAllByTopicIdForRelease(topicId, articleId);
    }

    @Override
    public List<ArticleEntity> findAllOrderNewsLimit() {
        return articleRepository.findAllOrderNewsLimit();
    }

    @Override
    public List<ArticleEntity> findAllOrderPopularLimit() {
        return articleRepository.findAllOrderPopularLimit();
    }

    @Override
    public List<CustomArticleDTO> getListNewestPost() {
        return articleRepository.getListNewestPost();
    }

    @Override
    public List<CustomArticleDTO> getListFavoritePost() {
        return articleRepository.getListFavPost();
    }

    private Map<Integer, Integer> getListMonth(Integer currentMonth, Integer currentYear) {
        Map<Integer, Integer> mapMonth = new HashMap<>();
        for (int i = 6; i > 0; i--) {
            if (currentMonth - i >= 0) {
                mapMonth.put(currentMonth - i + 1, currentYear);
            } else {
                mapMonth.put(currentMonth - i + 12 + 1, currentYear - 1);
            }
        }
        return mapMonth;
    }

    private Integer randomRGB() {
        Random random = new Random();
        return random.nextInt((255) + 1);
    }

//    private String getPath(Long id, String name){
//        return Constant.FIREBASE_URL + Constant.BUCKET_NAME + "/fileArticle/" + id + "/" + name;
//    }


}
