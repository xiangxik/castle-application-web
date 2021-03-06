package me.xiangxik.support.web;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.castle.integration.webapp.json.PathFilter;
import com.castle.repo.domain.Result;
import com.castle.repo.jpa.EntityService;
import com.querydsl.core.types.Predicate;

public abstract class EntityController<T, I extends Serializable> extends BaseController {

	@Autowired
	private EntityService<T, I> entityService;

	private String baseTemplatePath;

	private boolean inJar = false;

	public EntityController() {
		RequestMapping mapping = getClass().getAnnotation(RequestMapping.class);
		this.baseTemplatePath = mapping.value()[0];
	}

	public EntityController(String baseTemplatePath) {
		this.baseTemplatePath = baseTemplatePath;
	}

	public EntityController(boolean inJar) {
		this.inJar = inJar;
		RequestMapping mapping = getClass().getAnnotation(RequestMapping.class);
		this.baseTemplatePath = (inJar ? "classpath:" : "") + mapping.value()[0];
	}

	public EntityController(String baseTemplatePath, boolean inJar) {
		this.inJar = inJar;
		this.baseTemplatePath = (inJar ? "classpath:" : "") + baseTemplatePath;
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public abstract Page<T> doPage(Predicate predicate, Pageable pageable);
	
	protected Page<T> doInternalPage(Predicate predicate, Pageable pageable) {
		return getService().findAll(predicate, pageable); 
	}

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(Model model) {
		onShowIndexPage(model);
		return baseTemplatePath + "/index";
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String select(Model model) {
		return baseTemplatePath + "/select";
	}

	@RequestMapping(value = { "/add", "/edit" }, method = RequestMethod.GET)
	public String add(Model model) {
		return edit(getService().initDomain(), model);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") T entity, Model model) {
		model.addAttribute("entity", entity);
		onShowEditPage(entity, model);
		return baseTemplatePath + "/edit";
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") T entity, Model model) {
		model.addAttribute("entity", entity);
		onShowViewPage(entity, model);
		return baseTemplatePath + "/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid T entity, BindingResult bindingResult) {
		onValidate(entity, bindingResult);
		if (bindingResult.hasErrors()) {
			return Result.validateError().error(bindingResult.getAllErrors());
		}

		onBeforeSave(entity);
		getService().save(entity);
		onAfterSave(entity);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids[]") T[] entities) {
		if (entities != null) {
			for (T entity : entities) {
				onDelete(entity);
			}
		}

		return Result.success();
	}

	protected void onDelete(T entity) {
		if (onBeforeDelete(entity)) {
			getService().delete(entity);
		}
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	@PathFilter("*,*.id,*.name")
	public T getInfo(@RequestParam("id") T entity) {
		return entity;
	}

	protected void onShowIndexPage(Model model) {
	}

	protected void onShowEditPage(T entity, Model model) {
	}

	protected void onShowViewPage(T entity, Model model) {
	}

	protected void onValidate(T entity, BindingResult bindingResult) {
	}

	protected void onBeforeSave(T entity) {
	}

	protected void onAfterSave(T entity) {
	}

	protected boolean onBeforeDelete(T entity) {
		return true;
	}

	protected EntityService<T, I> getService() {
		return entityService;
	}

	protected String getBaseTemplatePath() {
		return baseTemplatePath;
	}

	protected boolean isInJar() {
		return inJar;
	}

}
