package me.xiangxik.content.repo;

import com.castle.repo.jpa.HierarchicalEntityRepository;

import me.xiangxik.content.entity.ArticleCategory;

public interface ArticleCategoryRepository extends HierarchicalEntityRepository<ArticleCategory, Long> {

}
