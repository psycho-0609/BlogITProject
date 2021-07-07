package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.CustomArticleDTO;
import com.finnal.blogit.dto.response.GetListReport;
import com.finnal.blogit.dto.response.ListReportArticleDTO;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import com.finnal.blogit.repository.ArticleReportRepository;
import com.finnal.blogit.repository.ArticleRepository;
import com.finnal.blogit.service.inter.IArticleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleReportService implements IArticleReportService {

    @Autowired
    private ArticleReportRepository repository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public ArticleReportEntity save(ArticleReportEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ArticleReportEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<GetListReport> findAll() {
        List<Long> ids = repository.getListArticleIdNews(ArticleReportNews.ENABLE);
        List<GetListReport> list = repository.getListReport();
        List<Long> listArticleId = list.stream().map(GetListReport::getArticleId).collect(Collectors.toList());
        List<CustomArticleDTO>  listArticle = articleRepository.getListArticle(listArticleId);
        Map<Long, List<CustomArticleDTO>> mapArticle = listArticle.stream().collect(Collectors.groupingBy(CustomArticleDTO::getId));
        list.forEach(t -> t.setArticle(mapArticle.get(t.getArticleId()).get(0)));
        for(Long id:ids){
            for(GetListReport item:list){
                if(item.getArticleId().equals(id)){
                    item.setNews(ArticleReportNews.ENABLE);
                    break;
                }
            }
        }

        return list;
    }

    @Override
    public List<ListReportArticleDTO> findAllReportByArticleId(Long id) {
        return repository.getListReportByArticleId(id);
    }

    @Override
    @Transactional
    public void deleteByArticleId(Long id) {
        repository.deleteAllByArticleEntityId(id);
    }

    @Override
    public List<ArticleReportEntity> findAllByArticleIdAndNews(Long id) {
        return repository.findAllByArticleEntityIdAndNews(id);
    }

    @Override
    public void saveAll(List<ArticleReportEntity> list) {
        repository.saveAll(list);
    }


}
