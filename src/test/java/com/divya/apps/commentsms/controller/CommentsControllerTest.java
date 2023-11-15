package com.divya.apps.commentsms.controller;


import com.divya.apps.comments.controller.CommentsController;
import com.divya.apps.comments.model.Comment;
import com.divya.apps.comments.service.CommentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsControllerTest  {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private CommentsService commentsService;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private CommentsController commentsController;


    void testCreateComment() throws Exception {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setMessage("Test comment");

        // Mock the commentsService to ensure the createComment method is called
        Mockito.doNothing().when(commentsService).createComment(any(Comment.class));

        // MockMvc setup
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(commentsController).build();

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the createComment method was called once
        verify(commentsService, times(1)).createComment(any(Comment.class));

        // Verify that the simpMessagingTemplate.convertAndSend method was called once with the correct arguments
        verify(simpMessagingTemplate, times(1)).convertAndSend("/topic/comment", comment);
    }


    void getCommentById() throws Exception {
        int commentId = 1; // Provide a comment ID for testing

        Comment comment = new Comment(); // Provide necessary data for the comment

        when(commentsService.getCommentById(commentId)).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/{id}", commentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(commentId));

        // Verify that the getCommentById method of commentsService is called once
        verify(commentsService, times(1)).getCommentById(commentId);
    }

    void getComments() throws Exception {
        List<Comment> comments = Arrays.asList(new Comment(), new Comment()); // Provide necessary data for comments

        when(commentsService.getComments()).thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(comments.size()));

        // Verify that the getComments method of commentsService is called once
        verify(commentsService, times(1)).getComments();
    }

    void deleteComments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the deleteComments method of commentsService is called once
        verify(commentsService, times(1)).deleteComments();
    }
}