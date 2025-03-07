package com.project.catalog.admin.application.category.retrieve.get;

import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.category.CategoryID;
import com.project.catalog.admin.domain.exceptions.DomainException;
import com.project.catalog.admin.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String anId) {

        final CategoryID id = CategoryID.from(anId);

        return categoryGateway.findById(id)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(id));
    }

    private Supplier<DomainException> notFound(final CategoryID id) {
        return () -> DomainException.with(Error.from("Category with ID %s was not found", id));
    }
}
