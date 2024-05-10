package com.mjc.school.service;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    List<AuthorDtoResponse> modelListToDtoList(List<Author> authorList);

    AuthorDtoResponse modelToDto(Author author);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "lastUpdateTime", ignore = true)
    @Mapping(target = "newsList", ignore = true)
    Author dtoToModel(AuthorDtoRequest dto);
}
