package com.divya.apps.commentsms.service;

import com.divya.apps.comments.model.Comment;
import com.divya.apps.comments.repository.CommentRepository;
import com.divya.apps.comments.service.CommentsServiceImpl;
import com.divya.apps.comments.service.IDSequenceGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

public class CommentsServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IDSequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private CommentsServiceImpl commentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentById() {
        // Mock data
        int commentId = 1;
        Comment mockComment = new Comment();
        mockComment.setId(commentId);
        mockComment.setCreated(OffsetDateTime.now(ZoneOffset.UTC));

        // Mocking the behavior of CommentRepository
        when(commentRepository.findById(commentId)).thenReturn(java.util.Optional.of(mockComment));

        // Call the method
        Comment result = commentsService.getCommentById(commentId);

        // Assertions
        assertNotNull(result);
        assertEquals(commentId, result.getId());
        assertEquals(mockComment.getCreated(), result.getCreated());

        // Verify that the repository method was called with the correct argument
        verify(commentRepository).findById(commentId);
    }

    @Test
    void testGetComments() {
        // Mock data
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setCreated(OffsetDateTime.now(ZoneOffset.UTC));

        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setCreated(OffsetDateTime.now(ZoneOffset.UTC).minusDays(1));

        List<Comment> mockComments = Arrays.asList(comment1, comment2);

        // Mocking the behavior of CommentRepository
        when(commentRepository.findAll()).thenReturn(mockComments);

        // Call the method
        List<Comment> result = commentsService.getComments();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(comment1, result.get(0));
        assertEquals(comment2, result.get(1));

        // Verify that the repository method was called
        verify(commentRepository).findAll();
    }

    @Test
    void testCreateComment() {
        // Mock data
        Comment commentToCreate = new Comment();
        commentToCreate.setId(1);

        // Mocking the behavior of IDSequenceGeneratorService
        when(sequenceGeneratorService.generateSequence("comment")).thenReturn(1);

        // Call the method
        Comment result = commentsService.createComment(commentToCreate);

        // Assertions
        assertNotNull(result);
        assertNotNull(result.getCreated());
        assertEquals(1, result.getId());

        // Verify that the repository method was called with the correct argument
        verify(commentRepository).save(commentToCreate);
    }

    @Test
    void testDeleteComments() {
        // Call the method
        commentsService.deleteComments();

        // Verify that the repository method was called
        verify(commentRepository).deleteAll();
    }
}
