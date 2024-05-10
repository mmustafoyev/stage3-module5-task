package com.mjc.school.service;

import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    List<CommentDtoResponse> listModelToDto(List<Comment> comments);

    @Mapping(source = "news", target = "newsDtoResponse")
    CommentDtoResponse modelToDto(Comment comment);
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastUpdateTime", ignore = true)
    @Mapping(target = "news", ignore = true)
    Comment dtoToModel(CommentDtoRequest commentRequest);
}
