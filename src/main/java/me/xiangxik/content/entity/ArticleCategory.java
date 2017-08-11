package me.xiangxik.content.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.castle.repo.jpa.HierarchicalEntity;

import me.xiangxik.user.entity.User;

@Entity
@Table(name = "tbl_article_category")
public class ArticleCategory extends HierarchicalEntity<User, Long, ArticleCategory> {

	private static final long serialVersionUID = -1184664942624917482L;

	private String name;

	@Column(nullable = false)
	private int articleCount = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

}