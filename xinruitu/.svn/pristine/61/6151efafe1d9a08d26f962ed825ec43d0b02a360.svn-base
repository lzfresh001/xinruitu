package com.yx.xinruitu.intercept;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorAdapter implements WebMvcConfigurer {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private UrlInterceptor urlInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("拦截");
		registry.addInterceptor(urlInterceptor).addPathPatterns("/**").excludePathPatterns("/","/login","/logout","/tologin","/error/**","/upload/show/**","/static/**","/wechat","/wx/**");
	}
}
