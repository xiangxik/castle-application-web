package me.xiangxik.content.repo;

import java.util.List;

import com.castle.repo.jpa.EntityRepository;

import me.xiangxik.content.entity.Article;

public interface ArticleRepository extends EntityRepository<Article, Long> {

	List<Article> findTop5ByPublishedTrueOrderByPublishedDateDesc();
}
