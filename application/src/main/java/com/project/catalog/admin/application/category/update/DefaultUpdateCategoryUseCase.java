package com.project.catalog.admin.application.category.update;

import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.category.CategoryID;
import com.project.catalog.admin.domain.exceptions.DomainException;
import com.project.catalog.admin.domain.validation.Error;
import com.project.catalog.admin.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand command) {

        CategoryID id = CategoryID.from(command.id());

        final Category category = categoryGateway.findById(id).orElseThrow(notFound(id));

        final Notification notification = Notification.create();

        category
                .update(command.name(), command.description(), command.active())
                .validate(notification);

        return notification.hasError() ? Either.left(notification) : update(category);
    }

    private Supplier<DomainException> notFound(final CategoryID id) {
        return () -> DomainException.with(Error.from("Category with ID %d, was not found.", id));
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category) {
        return API.Try(() -> this.categoryGateway.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }
}
