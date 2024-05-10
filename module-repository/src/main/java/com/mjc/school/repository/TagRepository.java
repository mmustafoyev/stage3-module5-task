package com.mjc.school.repository;

import com.mjc.school.repository.model.Tag;

import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag, Long> {
    Optional<Tag> readByNewsId(Long newsId);

}
