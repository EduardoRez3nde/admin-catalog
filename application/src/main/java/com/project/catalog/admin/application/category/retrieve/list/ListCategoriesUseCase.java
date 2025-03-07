package com.project.catalog.admin.application.category.retrieve.list;

import com.project.catalog.admin.application.UseCase;
import com.project.catalog.admin.domain.pagination.Pagination;
import com.project.catalog.admin.domain.pagination.SearchQuery;

public abstract class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> { }
