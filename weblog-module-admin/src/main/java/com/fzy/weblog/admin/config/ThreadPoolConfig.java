package com.fzy.weblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
//@EnableAsync注解用于启用 Spring 的异步特性。
@EnableAsync
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);//线程池维护线程的最少数量
        executor.setMaxPoolSize(20);//线程池维护线程的最大数量
        executor.setQueueCapacity(100);//线程池所使用的缓冲队列
        executor.setThreadNamePrefix("WeblogThreadPool-");//线程池中线程的名称前缀
        executor.initialize();//初始化线程池
        return executor;
    }
}
