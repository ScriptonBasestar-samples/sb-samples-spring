package kr.scripton.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

import java.util.List;

/**
 * @author archmagece
 * @since 2017-09-06
 */
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

	@Autowired
//	@Qualifier("thymeleafViewResolver")
	private List<ViewResolver> viewResolverList;

//	@Autowired
//	private AccessDecisionManager accessDecisionManager;

	@Bean
	public SecurityFlowExecutionListener securityFlowExecutionListener(){
		SecurityFlowExecutionListener bean =  new SecurityFlowExecutionListener();
//		bean.setAccessDecisionManager(accessDecisionManager);
		return bean;
	}

	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry())
				.addFlowExecutionListener(securityFlowExecutionListener(), "*")
				.build();
	}

	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder(flowBuilderServices())
				.setBasePath("classpath*:/templates")
				.addFlowLocationPattern("/**/*-flow.xml")
				.build();
	}

	@Bean
	public FlowBuilderServices flowBuilderServices() {
		return getFlowBuilderServicesBuilder()
				.setViewFactoryCreator(mvcViewFactoryCreator())
				.build();
	}

	@Bean
	public MvcViewFactoryCreator mvcViewFactoryCreator() {
		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
		factoryCreator.setViewResolvers(viewResolverList);
//		factoryCreator.setViewResolvers(ListBuilder.<ViewResolver>create(tilesViewResolver()).build());
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}

	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
//		handlerMapping.setOrder(-1);
//		handlerMapping.setFlowUrlHandler();
//		handlerMapping.setFlowRegistry(flowRegistry());
		handlerMapping.setFlowRegistry(flowRegistry());
		return handlerMapping;
	}

	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
		handlerAdapter.setFlowExecutor(flowExecutor());
		handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
		return handlerAdapter;
	}


//	@Bean(name = "signup")
//	public SignupFlowHandler signupFlowHandler(){
//		return new SignupFlowHandler();
//	}

}