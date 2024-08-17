package com.example.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 스프링부트 어플리케이션의 시작을 담당하는 파일 {프로젝트명}+Application.java => 스프링부트 프로젝트를 생성하면 자동으로 생성됨
@SpringBootApplication
@EnableJpaAuditing // 이게 있어야 TimeStamp Entity가 정상동작한다
public class TemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}

}
