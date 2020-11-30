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
		
		// Uno para el despliegue(1), otro para localhost(2).
		 Path fotoUploadDir = Paths.get("./webapps/ROOT/WEB-INF/classes/static/img/artista-fotos");
//		Path fotoUploadDir = Paths.get("./src/main/resources/static/img/artista-fotos");

		String fotoUploadPath = fotoUploadDir.toFile().getAbsolutePath();
		
		// Uno para el despliegue(1), otro para localhost(2).
		 registry.addResourceHandler("/webapps/ROOT/WEB-INF/classes/static/img/artista-fotos/**").addResourceLocations("file:/" + fotoUploadPath + "/");
//		registry.addResourceHandler("/src/main/resources/static/img/artista-fotos/**").addResourceLocations("file:/" + fotoUploadPath + "/");
	}
}
