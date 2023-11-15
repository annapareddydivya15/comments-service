package com.divya.apps.comments.controller;

import com.divya.apps.comments.api.CommentsApi;
import com.divya.apps.comments.model.Comment;
import com.divya.apps.comments.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class CommentsController implements CommentsApi {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @CrossOrigin
    @Override
    public ResponseEntity<Void> createComment(Comment comment)
    {
        commentsService.createComment(comment);

        simpMessagingTemplate.convertAndSend("/topic/comment", comment);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Comment> getCommentById(Integer commentId)
    {
        Comment comment = commentsService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    @Override
    public ResponseEntity<List<Comment>> getComments()
    {
        List<Comment> comments = commentsService.getComments();
        return ResponseEntity.ok(comments);
    }

    @Override
    public ResponseEntity<Void> deleteComments()
    {
        commentsService.deleteComments();
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/application")
    @SendTo("/topic/comment")
    public Comment send(final Comment comment)
    {
        return comment;
    }
}
