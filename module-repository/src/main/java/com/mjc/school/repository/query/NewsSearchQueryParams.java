package com.mjc.school.repository.query;

import java.util.List;

public record NewsSearchQueryParams(
        List<String> tagNames,
        List<Long> tagIds,
        String author,
        String title,
        String content
) {}
