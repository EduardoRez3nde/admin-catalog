package com.project.catalog.admin.application.category.create;

import com.project.catalog.admin.application.UseCase;
import com.project.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {  }
