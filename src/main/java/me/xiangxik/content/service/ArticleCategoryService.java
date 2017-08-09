package me.xiangxik.content.service;

import org.springframework.stereotype.Service;

import com.castle.repo.jpa.HierarchicalEntityService;

import me.xiangxik.content.entity.ArticleCategory;

@Service
public class ArticleCategoryService extends HierarchicalEntityService<ArticleCategory, Long> {

}
