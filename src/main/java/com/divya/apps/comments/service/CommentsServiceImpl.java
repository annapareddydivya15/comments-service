package com.divya.apps.comments.service;

import com.divya.apps.comments.model.Comment;
import com.divya.apps.comments.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component(value = CommentsService.BEAN_NAME)
public class CommentsServiceImpl implements CommentsService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    IDSequenceGeneratorService sequenceGeneratorService;

    @Override
    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comments::add);
        Collections.sort(comments, (c1, c2) -> c2.getCreated().compareTo(c1.getCreated()));
        return comments;
    }

    @Override
    public Comment createComment(Comment comment) {
        comment.setCreated(OffsetDateTime.now(ZoneOffset.UTC));
        comment.setId(sequenceGeneratorService.generateSequence("comment"));
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public void deleteComments() {
        commentRepository.deleteAll();
    }
}
