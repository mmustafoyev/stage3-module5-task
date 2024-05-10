package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class, TagMapper.class})
public abstract class NewsMapper {

    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;

    public abstract List<NewsDtoResponse> modelListToDtoList(List<News> modelList);

    @Mapping(source = "author", target = "authorDto")
    @Mapping(source = "comments", target = "commentsDto")
    public abstract NewsDtoResponse modelToDto(News model);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastUpdateTime", ignore = true)
    @Mapping(target = "author", expression =
        "java(authorRepository.getReference(dto.authorId()))")
    @Mapping(target = "tags", expression =
        "java(dto.tagsIds().stream().map(tagId -> tagRepository.getReference(tagId)).toList())")
    @Mapping(target = "comments", ignore = true)
    public abstract News dtoToModel(NewsDtoRequest dto);



}
