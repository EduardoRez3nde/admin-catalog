package com.project.catalog.admin.application.category.delete;

import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    public void execute(final String id) {
        categoryGateway.deleteById(CategoryID.from(id));
    }
}
