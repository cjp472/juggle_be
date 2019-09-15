package com.juggle.juggle.config;

import com.juggle.juggle.framework.api.validation.JSValidInterceptor;
import com.juggle.juggle.framework.common.security.AccessCut;
import com.juggle.juggle.framework.common.validation.JSValidationFactoryBean;
import com.juggle.juggle.framework.common.validation.ValidRefCut;
import com.juggle.juggle.framework.data.json.ExtMapperFactory;
import com.juggle.juggle.api.ApiServlet;
import com.juggle.juggle.api.ProjectApiExceptionResolver;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DefaultApiConfig extends SpringBootServletInitializer implements WebMvcConfigurer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    @Bean
    public ServletRegistrationBean apiServlet() {
        DispatcherServlet servlet = new ApiServlet();
        servlet.setThrowExceptionIfNoHandlerFound(true);

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        servlet.setApplicationContext(applicationContext);

        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/*");
        bean.setName("defaultApi");
        Map<String, String> params = new HashMap<>();
        params.put("detectAllHandlerExceptionResolvers", "true");
        params.put("contextConfigLocation", "classpath*:spring/defaultApi.xml");
        bean.setInitParameters(params);
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @Bean
    public FilterRegistrationBean shiroFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        bean.addInitParameter("targetFilterLifecycle","true");
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH");
    }

    @Bean
    public HandlerExceptionResolver apiExceptionResolver() {
        return new ProjectApiExceptionResolver();
    }

    @Bean
    public AccessCut accessCut() {
        return new AccessCut();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JSValidInterceptor(jsValidationFactoryBean()));
    }

    @Bean
    public JSValidationFactoryBean jsValidationFactoryBean() {
        return new JSValidationFactoryBean();
    }

    @Bean
    public ValidRefCut validRefCut() {
        return new ValidRefCut();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(ExtMapperFactory.create());
        return converter;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setProviderClass(HibernateValidator.class);
        bean.setValidationMessageSource(messageSource);
        bean.setMessageInterpolator(new ResourceBundleMessageInterpolator() {

        });
        return bean;
    }
}
