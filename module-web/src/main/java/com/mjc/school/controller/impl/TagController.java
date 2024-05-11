package com.mjc.school.controller.impl;


import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@Api(produces = "aoolication/json", value = "for getting, creating, updating, deletion")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET TAGS", response = TagDtoResponse.class)
    @CommandHandler(operation = 7)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> getTags(int page, int size, String sortBy) {
        return tagService.readAll(page,size,sortBy);
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET TAG BY ID", response = TagDtoResponse.class)
    @CommandHandler(operation = 11)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse getTagById(@PathVariable Long id) {
        return tagService.readById(id);
    }


    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET TAG BY NEWS ID", response = TagDtoResponse.class)
    @CommandHandler(operation = 22)
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse getByNewsId(@PathVariable Long newsId) {
        return tagService.readByNewsId(newsId);
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 201, message = "CREATED"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "CREATE TAG", response = TagDtoResponse.class)
    @CommandHandler(operation = 3)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDtoResponse createTag(@RequestBody TagDtoRequest tagDtoRequest) {
        return tagService.create(tagDtoRequest);
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "UPDATE TAGS", response = TagDtoResponse.class)
    @CommandHandler(operation = 15)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse updateTag(@PathVariable Long id, @RequestBody TagDtoRequest tagDtoRequest) {
        return tagService.update(tagDtoRequest);
    }


    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "UPDATE TAGS", response = TagDtoResponse.class)
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse patch(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }


    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "DELETE TAGS")
    @CommandHandler(operation = 19)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
