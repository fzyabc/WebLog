package com.fzy.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class ReadArticleEvent extends ApplicationEvent {
    /**
     * 文章 ID
     */
    private Long articleId;

    public ReadArticleEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
}
