package com.mycompany.app.transaction;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"com.mycompany.app.transaction"})
@EnableAutoConfiguration
@EnableJpaRepositories
public class Application {
    @Autowired
    private Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @PostConstruct
    public void initApplication() throws IOException {
    	 if (env.getActiveProfiles().length == 0) {
             LOGGER.warn("No Spring profile configured, running with default configuration");
         } else {
             LOGGER.info("Running with Spring profile(s) : {}", env.getActiveProfiles());
         }
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
       /* ApplicationContext ctx = SpringApplication.run(Application.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }*/
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PreDestroy
    public void shutdownLogback(){
        LOGGER.info("Shutting down Logback");
        LoggerContext lCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
        lCtx.stop();
    }

}

