package com.mjc.school.controller.impl;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Api(produces = "aoolication/json", value = "for getting, creating, updating, deletion")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET COMMENTS", response = CommentDtoResponse.class)
    @CommandHandler(operation = 8)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> getComments(int page, int size, String sortBy) {
        return commentService.readAll(page,size,sortBy);
    }


    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET COMMENTS BY ID", response = CommentDtoResponse.class)
    @CommandHandler(operation = 12)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse getCommentById(@PathVariable Long id) {
        return commentService.readById(id);
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET COMMENTS BY NEWS ID", response = CommentDtoResponse.class)
    @CommandHandler(operation = 23)
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse getByNewsId(@PathVariable Long newsId) {
        return commentService.readByNewsId(newsId);
    }


    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 201, message = "CREATED"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "CREATE COMMENTS", response = CommentDtoResponse.class)
    @CommandHandler(operation = 4)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoResponse createComment(@RequestBody CommentDtoRequest commentDtoRequest) {
        return commentService.create(commentDtoRequest);
    }


    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "UPDATE COMMENTS", response = CommentDtoResponse.class)
    @CommandHandler(operation = 16)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse updateComment(@PathVariable Long id, @RequestBody CommentDtoRequest commentDtoRequest) {
        return commentService.update(commentDtoRequest);
    }



    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 201, message = "CREATED"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "CREATE COMMENTS")
    @CommandHandler(operation = 20)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}
