package com.mjc.school.controller.impl;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @CommandHandler(operation = 6)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDtoResponse> getAuthors(@RequestParam(defaultValue = "1", required = false) int page,
                                              @RequestParam(defaultValue = "10", required = false) int size,
                                              @RequestParam(name = "sort_by", defaultValue = "id::asc", required = false) String sortBy) {
        return authorService.readAll(page, size, sortBy);
    }
    @CommandHandler(operation = 10)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse getAuthorById(@PathVariable Long id) {
        return authorService.readById(id);
    }
    @CommandHandler(operation = 21)
    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse getByNewsId(@PathVariable Long newsId) {
        return authorService.readByNewsId(newsId);
    }
    @CommandHandler(operation = 2)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDtoResponse createAuthor(@RequestBody AuthorDtoRequest authorDtoRequest) {
        return authorService.create(authorDtoRequest);
    }
    @CommandHandler(operation = 14)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse updateAuthor(@PathVariable Long id, @RequestBody AuthorDtoRequest authorDtoRequest) {
        return authorService.update(authorDtoRequest);
    }
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse patch(@PathVariable Long id, @RequestBody AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }
    @CommandHandler(operation = 18)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}
