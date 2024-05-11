package com.mjc.school.controller.impl;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(produces = "aoolication/json", value = "for getting, creating, updating, deletion")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET NEWS LIST", response = NewsDtoResponse.class)
    @CommandHandler(operation = 5)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> getNewsList(int page, int size, String sortBy) {
        return newsService.readAll(page,size,sortBy);
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET NEWS BY ID", response = NewsDtoResponse.class)
    @CommandHandler(operation = 9)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse getNewsById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "GET NEWS BY PARAMS", response = NewsDtoResponse.class)
    @CommandHandler(operation = 24)
    @GetMapping("/one-news")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> getNewsByParams(
            @RequestParam(required = false) NewsQueryParams newsQueryParams
    ) {
        return newsService.readByQueryParams(newsQueryParams);
    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 201, message = "CREATED"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "CREATE NEWS", response = NewsDtoResponse.class)
    @CommandHandler(operation = 1)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDtoResponse createNews(@RequestBody NewsDtoRequest newsDtoRequest) {
        return newsService.create(newsDtoRequest);
    }
    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "UPDATE NEWS", response = NewsDtoResponse.class)
    @CommandHandler(operation = 13)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse updateNews(@PathVariable Long id, @RequestBody NewsDtoRequest newsDtoRequest) {
        return newsService.update(newsDtoRequest);
    }
//    @PatchMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public NewsDtoResponse patch(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
//        return newsService.update(updateRequest);
//    }

    @ApiResponses(
            value ={
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "NO CONTENT"),
                    @ApiResponse(code = 404, message = "NOT FOUND")

            }
    )
    @ApiOperation(value = "DELETE NEWS")
    @CommandHandler(operation = 17)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
    }
}
