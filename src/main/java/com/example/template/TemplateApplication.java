package com.example.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 스프링부트 어플리케이션의 시작을 담당하는 파일 {프로젝트명}+Application.java => 스프링부트 프로젝트를 생성하면 자동으로 생성됨
@SpringBootApplication
public class TemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}

}
