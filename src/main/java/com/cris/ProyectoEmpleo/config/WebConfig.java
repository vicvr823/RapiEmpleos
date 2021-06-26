package com.cris.ProyectoEmpleo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Value("${empleos.ruta.imagenes}")
	private String rutaimg;
	
	@Value("${empleos.ruta.cv}")
	private String rutaCv;
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Configuración de los recursos estáticos (imagenes de las vacantes) 
		//registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleos/img-vacantes/"); // Windows
		registry.addResourceHandler("/logos/**").addResourceLocations("file:"+rutaimg);
		
		// Configuración de los recursos estáticos (archivos de los CV)
		//registry.addResourceHandler("/cv/**").addResourceLocations("file:c:/empleos/files-cv/"); // Windows
		registry.addResourceHandler("/cv/**").addResourceLocations("file:"+rutaCv); // Linux
	}
	
}
