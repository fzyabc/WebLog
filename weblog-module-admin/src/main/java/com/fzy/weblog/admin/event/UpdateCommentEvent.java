package com.fzy.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateCommentEvent extends ApplicationEvent {
    private Long commentId;
    public UpdateCommentEvent(Object source,Long commentId) {
        super(source);
        this.commentId = commentId;
    }
}
