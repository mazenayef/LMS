package org.lms.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// async service executor, created independantly and will be injected to other manifesters
// this was test releated injected in services with async methods execution will be removed in production
@Configuration
public class ExecutorServiceConfig {
    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10); // will give each repo 10 reusable threads for now
    }
}
