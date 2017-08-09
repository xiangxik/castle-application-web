package me.xiangxik.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.querydsl.core.types.Predicate;

import me.xiangxik.content.entity.Article;
import me.xiangxik.content.service.ArticleTagService;
import me.xiangxik.support.web.EntityController;

@Controller
@RequestMapping("/article")
public class ArticleController extends EntityController<Article, Long> {

	@Autowired
	private ArticleTagService articleTagService;

	@Override
	public Page<Article> doPage(Predicate predicate, Pageable pageable) {
		return super.doInternalPage(predicate, pageable);
	}

	@Override
	protected void onShowEditPage(Article entity, Model model) {
		super.onShowEditPage(entity, model);

		model.addAttribute("tags", articleTagService.findAll());
	}

}
