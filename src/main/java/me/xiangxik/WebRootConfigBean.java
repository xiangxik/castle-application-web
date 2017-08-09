package me.xiangxik;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.castle.repo.jpa.EntityRepository;
import com.castle.repo.jpa.EntityRepositoryFactoryBean;
import com.castle.web.ServletSupport;

@Configuration
@ComponentScan(basePackages = { "me.xiangxik" }, excludeFilters = { @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = EnableWebMvc.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = ServletSupport.class, type = FilterType.ANNOTATION) })
@EnableJpaRepositories(basePackages = { "me.xiangxik" }, includeFilters = {
		@Filter(value = EntityRepository.class, type = FilterType.ASSIGNABLE_TYPE) }, repositoryImplementationPostfix = "Impl", repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
public class WebRootConfigBean {

}
