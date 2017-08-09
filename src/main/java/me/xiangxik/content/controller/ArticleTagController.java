package me.xiangxik.content.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.querydsl.core.types.Predicate;

import me.xiangxik.content.entity.ArticleTag;
import me.xiangxik.support.web.EntityController;

@Controller
@RequestMapping("/articleTag")
public class ArticleTagController extends EntityController<ArticleTag, Long> {

	@Override
	public Page<ArticleTag> doPage(Predicate predicate, Pageable pageable) {
		return super.doInternalPage(predicate, pageable);
	}

}
