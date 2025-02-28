package com.project.catalog.admin.application;

import com.project.catalog.admin.domain.category.Category;

public class UseCase {

    public Category execute() {
        return Category.newCategory("name", "description", true);
    }
}