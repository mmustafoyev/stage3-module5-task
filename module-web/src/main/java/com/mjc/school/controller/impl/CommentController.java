package com.mjc.school.controller.impl;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @CommandHandler(operation = 8)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> getComments(int page, int size, String sortBy) {
        return commentService.readAll(page,size,sortBy);
    }
    @CommandHandler(operation = 12)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse getCommentById(@PathVariable Long id) {
        return commentService.readById(id);
    }
    @CommandHandler(operation = 23)
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse getByNewsId(@PathVariable Long newsId) {
        return commentService.readByNewsId(newsId);
    }
    @CommandHandler(operation = 4)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoResponse createComment(@RequestBody CommentDtoRequest commentDtoRequest) {
        return commentService.create(commentDtoRequest);
    }
    @CommandHandler(operation = 16)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse updateComment(@PathVariable Long id, @RequestBody CommentDtoRequest commentDtoRequest) {
        return commentService.update(commentDtoRequest);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse patch(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }
    @CommandHandler(operation = 20)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}
