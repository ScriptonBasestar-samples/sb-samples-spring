package org.beansugar.samples.jpa.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author archmagece
 * @since 2014. 7. 25.
 * beansguar.org
 * http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-one-configuration
 * 여기 튜토리얼 참조해서 취향에따라 변경.
 */
@Configuration
@ComponentScan(basePackages = {"org.beansugar.samples.jpa.controller"})
@EnableWebMvc
@ImportResource("classpath:META-INF/root/spring-context.xml")
@PropertySource("classpath:META-INF/root/database.hsql.properties")
public class BaseConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Resource
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.PoolProperties p = new org.apache.tomcat.jdbc.pool.PoolProperties();
		p.setUrl(environment.getRequiredProperty("db.url"));
		p.setDriverClassName(environment.getRequiredProperty("db.driver"));
		p.setUsername(environment.getRequiredProperty("db.username"));
		p.setPassword(environment.getRequiredProperty("db.password"));

		p.setJmxEnabled(true);
		p.setTestWhileIdle(true);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(200);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);

		DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(p);
		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager() throws ClassNotFoundException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws ClassNotFoundException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("org.beansugar.samples.jpa.model");
		//TODO Deprecated변경후 주석처리, 테스트후삭제
//		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		jpaProperties.put("hibernate.ejb.naming_strategy", environment.getRequiredProperty("hibernate.ejb.naming_strategy"));
		jpaProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		jpaProperties.put("hibernate.HBM2DDL", environment.getRequiredProperty("hibernate.HBM2DDL"));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		messageSource.setBasename(environment.getRequiredProperty("message.source.basename"));
		messageSource.setUseCodeAsDefaultMessage(Boolean.parseBoolean(environment.getRequiredProperty("message.source.use.code.as.default.message")));

		return messageSource;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}
}
