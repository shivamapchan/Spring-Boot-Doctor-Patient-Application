package com.app.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "doctor-photos";
		Path doctorPhotoDir = Paths.get(dirName);
		String doctorPhotosPath = doctorPhotoDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" +doctorPhotosPath + "/");
		// TODO Auto-generated method stub
		//WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
	

}
