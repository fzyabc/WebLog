package com.fzy.weblog.admin.event.subscriber;

import com.fzy.weblog.admin.event.ReadArticleEvent;
import com.fzy.weblog.common.domain.mapper.ArticleMapper;
import com.fzy.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class ReadArticleSubscriber implements ApplicationListener<ReadArticleEvent> {
    @Autowired
    private ArticleMapper articleMapper;
@Autowired
private StatisticsArticlePVMapper articlePVMapper;
    @Override
    //方法的头部添加了 @Async("threadPoolTaskExecutor") 异步注解，
    // 表示该方法将通过异步线程来执行，使用的是名为 threadPoolTaskExecutor 的线程池。
    @Async("threadPoolTaskExecutor")
    public void onApplicationEvent(ReadArticleEvent event) {
        // 在这里处理收到的事件，可以是任何逻辑操作
        Long articleId = event.getArticleId();
        // 获取当前线程名称
        String threadName = Thread.currentThread().getName();
        log.info("==> threadName: {}", threadName);
        log.info("==> 文章阅读事件消费成功，articleId: {}", articleId);
        // 当日文章 PV 访问量 +1
        LocalDate currentDate = LocalDate.now();
articlePVMapper.increasePVCount(currentDate);
        articleMapper.increaseReadNum(articleId);
    }
}
