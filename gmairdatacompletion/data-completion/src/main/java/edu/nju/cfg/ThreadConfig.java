package edu.nju.cfg;

import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @author Bright Chan
 * @date 2020/2/27 13:49
 */

@Component
@EnableAsync
public class ThreadConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(20);    //默认等待队列的长度为Integer.MAX，所以最大线程数设置几乎没有意义
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setThreadNamePrefix("dataCompletion-");
        executor.initialize();
        System.out.println("开启线程池");
        return executor;
    }
}
