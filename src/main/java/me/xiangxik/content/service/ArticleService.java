package me.xiangxik.content.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castle.repo.jpa.EntityService;

import me.xiangxik.content.entity.Article;
import me.xiangxik.content.repo.ArticleRepository;

@Service
public class ArticleService extends EntityService<Article, Long> {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	public List<Article> findTop5() {
		return articleRepository.findTop5ByPublishedTrueOrderByPublishedDateDesc();
	}
	
	@Override
	public <S extends Article> S save(S entity) {
		if(entity.getPublishedDate() == null && entity.isPublished()) {
			entity.setPublishedDate(new Date());
		}
		return super.save(entity);
	}
}
