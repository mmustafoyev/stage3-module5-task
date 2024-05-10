package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDBRepository extends AbstractDBRepository<Comment, Long> implements CommentRepository {
    @Override
    void update(Comment prevState, Comment nextState) {
        prevState.setContent(nextState.getContent());
    }

    @Override
    public Optional<Comment> readByNewsId(Long newsId) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "SELECT c FROM Comment c WHERE c.news.id = :newsId", Comment.class
        ).setParameter("newsId", newsId);
        try {
            Comment comment = query.getSingleResult();
            return Optional.of(comment);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}