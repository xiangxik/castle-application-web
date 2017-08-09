package me.xiangxik.content.service;

import org.springframework.stereotype.Service;

import com.castle.repo.jpa.EntityService;

import me.xiangxik.content.entity.Article;

@Service
public class ArticleService extends EntityService<Article, Long> {

}
