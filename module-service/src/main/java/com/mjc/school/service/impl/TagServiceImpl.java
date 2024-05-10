package com.mjc.school.service.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.TagMapper;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exception.ServiceErrorCode.TAG_DOES_NOT_EXIST_FOR_NEWS_ID;
import static com.mjc.school.service.exception.ServiceErrorCode.TAG_ID_DOES_NOT_EXIST;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readAll(int page, int size, String sortBy) {
        return tagMapper.listModelToDto(tagRepository.readAll(page,size,sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse readById(Long id) {
        return tagRepository.readById(id)
                .map(tagMapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id)
                        )
                );
    }

    @Override
    @Transactional
    public TagDtoResponse create(TagDtoRequest createRequest) {
        Tag tag = tagMapper.dtoToModel(createRequest);
        tag = tagRepository.create(tag);
        return tagMapper.modelToDto(tag);
    }

    @Override
    @Transactional
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        if(tagRepository.existsById(updateRequest.id())) {
            Tag tag = tagMapper.dtoToModel(updateRequest);
            tag = tagRepository.update(tag);
            return tagMapper.modelToDto(tag);
        }
        else
            throw new NotFoundException(
                    String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id())
            );
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (tagRepository.existsById(id)) {
            return tagRepository.deleteById(id);
        }
        else
            throw new NotFoundException(
                    String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id)
            );

    }

    @Override
    @Transactional
    public TagDtoResponse readByNewsId(Long newsId) {
        return tagRepository.readByNewsId(newsId)
                .map(tagMapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(TAG_DOES_NOT_EXIST_FOR_NEWS_ID.getMessage(), newsId))
                );
    }
}
