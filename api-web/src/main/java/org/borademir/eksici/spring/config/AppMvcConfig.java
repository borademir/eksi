package org.borademir.eksici.spring.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
class AppMvcConfig extends WebMvcConfigurationSupport {
	
    @SuppressWarnings("rawtypes")
	@Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for(HttpMessageConverter converter: converters) {
            if(converter instanceof MappingJackson2HttpMessageConverter) {
            	MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
            	jacksonConverter.setPrettyPrint(true);
                ObjectMapper mapper = jacksonConverter.getObjectMapper();
                mapper.setSerializationInclusion(Include.NON_NULL);
            }
        }
        
    }
    
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("PUT", "DELETE", "GET", "POST")
        .allowedHeaders("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With", "requestId", "Correlation-Id")
        .allowCredentials(false).maxAge(3600);
    }
    
}