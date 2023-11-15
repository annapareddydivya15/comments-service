package com.divya.apps.comments.service;

import com.divya.apps.comments.model.Comment;

import java.util.List;

public interface CommentsService {

    public static final String BEAN_NAME = "CommentsService";

    Comment getCommentById(int commentId);

    List<Comment> getComments();

    Comment createComment(Comment comment);

    void deleteComments();
}
