package com.project.catalog.admin.domain;

import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.exceptions.DomainException;
import com.project.catalog.admin.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void givenAValidParams_whenCallNewCategory_thenInstantiateCategory() {

        final String expectedName = "Filmes";
        final String expectedDescription = "Filmes em geral";
        final Boolean expectedActive = true;

        Category expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertNotNull(expectedCategory);
        Assertions.assertNotNull(expectedCategory.getId());
        Assertions.assertNotNull(expectedCategory.getCreatedAt());
        Assertions.assertNotNull(expectedCategory.getUpdatedAt());
        Assertions.assertNull(expectedCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, expectedCategory.getName());
        Assertions.assertEquals(expectedDescription, expectedCategory.getDescription());
        Assertions.assertEquals(expectedActive, expectedCategory.getActive());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {

        final var expectedName = "  ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

}
