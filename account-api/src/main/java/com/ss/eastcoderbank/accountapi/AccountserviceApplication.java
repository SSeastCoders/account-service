package com.ss.eastcoderbank.accountapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ss.eastcoderbank.*", exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "com.ss.eastcoderbank.core.model")
@EnableJpaRepositories(basePackages = "com.ss.eastcoderbank.*")
public class AccountserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountserviceApplication.class, args);
    }

}
