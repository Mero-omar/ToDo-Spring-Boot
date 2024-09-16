package org.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class TodoApplication {
//    @Bean
//    public ViewResolver viewResolver(){
//        InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
//        viewResolver.setPrefix("/webapp/WEB-INF");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

}
