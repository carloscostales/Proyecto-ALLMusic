package com.carlos.configuracion;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.carlos.enums.RutaArchivos;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		 Path fotoUploadDir = Paths.get("." + RutaArchivos.LOCALHOST.toString());

		String fotoUploadPath = fotoUploadDir.toFile().getAbsolutePath();
		
		 registry.addResourceHandler(RutaArchivos.LOCALHOST.toString() + "**").addResourceLocations("file:/" + fotoUploadPath + "/");
	}
	
}
