package com.project.catalog.admin.application.category.update;

import com.project.catalog.admin.application.UseCase;
import com.project.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> { }
