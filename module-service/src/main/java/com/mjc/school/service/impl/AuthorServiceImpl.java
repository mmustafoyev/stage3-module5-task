package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.AuthorMapper;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exception.ServiceErrorCode.AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID;
import static com.mjc.school.service.exception.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper mapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper mapper) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDtoResponse> readAll(int page, int size, String sortBy) {
        return mapper.modelListToDtoList(authorRepository.readAll(page,size,sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readById(Long id) {
        return authorRepository.readById(id).
                map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        AUTHOR_ID_DOES_NOT_EXIST.getMessage(),
                                        id)
                        )
                );
    }

    @Override
    @Transactional
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        Author author = mapper.dtoToModel(createRequest);
        author = authorRepository.create(author);
        return mapper.modelToDto(author);
    }

    @Override
    @Transactional
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        if(authorRepository.existsById(updateRequest.id())){
            Author author = mapper.dtoToModel(updateRequest);
            author = authorRepository.update(author);
            return mapper.modelToDto(author);
        }
        else
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(authorRepository.existsById(id))
            return authorRepository.deleteById(id);
        else
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readByNewsId(Long newsId) {
        return authorRepository.readByNewsId(newsId).
                map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getMessage(), newsId))
                );
    }
}
