package com.project.catalog.admin.domain.category;

import com.project.catalog.admin.domain.pagination.SearchQuery;
import com.project.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    void deleteById(CategoryID id);

    Optional<Category> findById(CategoryID id);

    Category update(Category category);

    Pagination<Category> findAll(SearchQuery query);
}
