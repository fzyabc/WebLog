package com.fzy.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

@Getter
public class DeleteArticleEvent extends ApplicationEvent {
    /**
     * 文章 ID
     */
    private Long articleId;
public DeleteArticleEvent(Object source,Long articleId){
super(source);
this.articleId=articleId;

}
}
