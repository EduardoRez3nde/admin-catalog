package com.project.catalog.admin.domain.category;

import com.project.catalog.admin.domain.validation.Error;
import com.project.catalog.admin.domain.validation.ValidationHandler;
import com.project.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;

    private final Category category;

    protected CategoryValidator(final Category category, ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstraint();
    }



    private void checkNameConstraint() {

        String nameCategory = category.getName();

        if (nameCategory == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (nameCategory.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        if (nameCategory.trim().length() < NAME_MIN_LENGTH || nameCategory.trim().length() > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
