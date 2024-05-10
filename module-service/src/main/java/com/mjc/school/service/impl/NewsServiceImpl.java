package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import com.mjc.school.service.NewsMapper;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exception.ServiceErrorCode.*;


@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;

    private final NewsMapper mapper;

    @Autowired
    public NewsServiceImpl(
        NewsRepository newsRepository,
        AuthorRepository authorRepository,
        TagRepository tagRepository,
        NewsMapper mapper
    ) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readAll(int page, int size, String sortBy) {
        return mapper.modelListToDtoList(newsRepository.readAll(page,size,sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDtoResponse readById(final Long id) {
        return newsRepository.readById(id)
            .map(mapper::modelToDto)
            .orElseThrow(
                () -> new NotFoundException(
                    String.format(
                        NEWS_ID_DOES_NOT_EXIST.getMessage(),
                        id
                    )
                )
            );
    }

    @Override
    @Transactional
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        if (!authorRepository.existsById(createRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), createRequest.authorId()));
        }

        Long nonExistentTagId = findFirstNonExistentTagId(createRequest.tagsIds());
        if (nonExistentTagId != null) {
            throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), nonExistentTagId));
        }

        News model = mapper.dtoToModel(createRequest);
        model = newsRepository.create(model);
        return mapper.modelToDto(model);

    }

    @Override
    @Transactional
    public NewsDtoResponse update( NewsDtoRequest updateRequest) {
        if (!authorRepository.existsById(updateRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.authorId()));
        }

        if (!newsRepository.existsById(updateRequest.id())) {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }

        Long nonExistentTagId = findFirstNonExistentTagId(updateRequest.tagsIds());
        if (nonExistentTagId != null) {
            throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), nonExistentTagId));
        }

        News model = mapper.dtoToModel(updateRequest);
        model = newsRepository.update(model);
        return mapper.modelToDto(model);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (newsRepository.existsById(id)) {
            return newsRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    private Long findFirstNonExistentTagId(List<Long> tagsIds) {
        return tagsIds.stream()
            .filter(id -> !tagRepository.existsById(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readByQueryParams(NewsQueryParams queryParams) {
        NewsSearchQueryParams searchQueryParams = new NewsSearchQueryParams(
            queryParams.tagNames(),
            queryParams.tagIds(),
            queryParams.authorName(),
            queryParams.title(),
            queryParams.content());
        return mapper.modelListToDtoList(newsRepository.readBySearchParam(searchQueryParams));
    }
}
