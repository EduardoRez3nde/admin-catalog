package com.project.catalog.admin.application.category.retrieve.list;

import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.pagination.Pagination;
import com.project.catalog.admin.domain.pagination.SearchQuery;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery searchQuery) {
        return categoryGateway
                .findAll(searchQuery)
                .map(CategoryListOutput::from);
    }
}
