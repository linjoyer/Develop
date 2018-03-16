package com.tch;

import com.tch.common.jwt.MyAudience;
import com.tch.dao.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created with IntelliJ IDEA.
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(MyAudience.class)
@EnableJpaRepositories(repositoryBaseClass=BaseRepositoryImpl.class)
public class SpirngbootApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        logger.info("加载servlet容器启动类：SpirngbootApplicationc.class");
        return application.sources(SpirngbootApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpirngbootApplication.class, args);
    }

}

