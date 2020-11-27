package com.gaborcsikos.fullstack.datarest;

import com.gaborcsikos.fullstack.todo.Todo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class Configurations {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Todo.class);
            config.setReturnBodyForPutAndPost(true);
        });
    }
}
