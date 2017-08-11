package me.xiangxik.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;

import me.xiangxik.content.entity.Article;
import me.xiangxik.content.entity.ArticleCategory;
import me.xiangxik.content.entity.ArticleTag;
import me.xiangxik.content.service.ArticleCategoryService;
import me.xiangxik.content.service.ArticleService;
import me.xiangxik.content.service.ArticleTagService;
import me.xiangxik.support.web.BaseController;
import me.xiangxik.user.entity.User;
import me.xiangxik.user.service.UserService;

@Controller
public class CommonController extends BaseController {

	@Autowired
	private ArticleTagService articleTagService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleCategoryService articleCategoryService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(Pageable pageable, Model model) {
		model.addAttribute("posts", articleService.findAll(pageable));
		return "/index";
	}

	@RequestMapping(value = "/p/{id}", method = RequestMethod.GET)
	public String detailPage(@PathVariable("id") Article article, Model model) {
		model.addAttribute("post", article);
		return "/detail";
	}

	@RequestMapping(value = "/p", method = RequestMethod.POST)
	@ResponseBody
	public Page<Article> doPage(Predicate predicate, Pageable pageable) {
		return articleService.findAll(predicate, pageable);
	}

	@ModelAttribute("tags")
	public List<ArticleTag> getTags() {
		return articleTagService.findAll();
	}

	@ModelAttribute("recentPosts")
	public List<Article> getRecentPosts() {
		return articleService.findTop5();
	}

	@ModelAttribute("categories")
	public List<ArticleCategory> getCategories() {
		return articleCategoryService.findRoots();
	}

	@ModelAttribute("authors")
	public List<User> getAuthors() {
		return userService.findTop5Post();
	}

}
