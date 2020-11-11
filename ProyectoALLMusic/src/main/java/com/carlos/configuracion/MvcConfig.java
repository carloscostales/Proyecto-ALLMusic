package com.carlos.configuracion;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path fotoUploadDir = Paths.get("./artista-fotos");
		
		String fotoUploadPath = fotoUploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/artista-fotos/**").addResourceLocations("file:/" + fotoUploadPath + "/");
	}
}
