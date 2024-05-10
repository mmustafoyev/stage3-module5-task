package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class TagDBRepository extends AbstractDBRepository<Tag, Long> implements TagRepository{
    @Override
    void update(Tag prevState, Tag nextState) {
        prevState.setName(nextState.getName());
    }

    @Override
    public Optional<Tag> readByNewsId(Long newsId) {
        TypedQuery<Tag> query = entityManager.createQuery(
                "SELECT t FROM Tag t INNER JOIN t.newsList n WHERE t.newsList =: newsId", Tag.class
        ).setParameter("newsId", newsId);
        return Optional.ofNullable(query.getSingleResult());
    }
}
