package com.project.catalog.admin.application.category.create;

import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
