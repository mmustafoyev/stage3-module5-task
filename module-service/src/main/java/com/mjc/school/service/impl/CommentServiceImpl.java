package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.CommentMapper;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static com.mjc.school.service.exception.ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST;
import static com.mjc.school.service.exception.ServiceErrorCode.COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readAll(int page, int size, String sortBy) {
        return commentMapper.listModelToDto(commentRepository.readAll(page,size,sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readById(Long id) {
        return commentRepository.readById(id).
                map(commentMapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id)
                        )
                );
    }

    @Override
    @Transactional
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        Comment comment = commentMapper.dtoToModel(createRequest);
        comment = commentRepository.create(comment);
        return commentMapper.modelToDto(comment);
    }

    @Override
    @Transactional
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        if(commentRepository.existsById(updateRequest.id())){
            Comment comment = commentMapper.dtoToModel(updateRequest);
            comment = commentRepository.update(comment);
            return commentMapper.modelToDto(comment);
        }
        else
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(commentRepository.existsById(id))
            return commentRepository.deleteById(id);
        else
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id));
    }

    @Override
    @Transactional
    public CommentDtoResponse readByNewsId(Long newsId) {
        return commentRepository.readByNewsId(newsId)
                .map(commentMapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID.getMessage(), newsId))
                );
    }
}
