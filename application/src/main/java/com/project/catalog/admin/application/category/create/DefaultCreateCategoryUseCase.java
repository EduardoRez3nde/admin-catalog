package com.project.catalog.admin.application.category.create;

import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand command) {
        final Category category = Category.newCategory(command.name(), command.description(), command.isActive());
        category.validate(new ThrowsValidationHandler());
        return CreateCategoryOutput.from(this.categoryGateway.create(category));
    }
}
