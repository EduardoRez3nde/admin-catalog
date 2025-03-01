package com.project.catalog.admin.domain;

import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.exceptions.DomainException;
import com.project.catalog.admin.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

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
        Assertions.assertEquals(expectedActive, expectedCategory.isActive());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {

        final String expectedName = null;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be null";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;

        final Category actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final DomainException actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {

        final String expectedName = "  ";
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be empty";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;

        final Category actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final DomainException actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {

        final String expectedName = "Fi ";
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;

        final Category actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final DomainException actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {

        final String expectedName = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore 
                magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo 
                consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla 
                pariatur. """;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;

        final Category actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final DomainException actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveError() {

        final String expectedName = "Filmes";
        final int expectedErrorCount = 1;
        final String expectedDescription = " ";
        final boolean expectedIsActive = true;

        Category expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(expectedCategory);
        Assertions.assertNotNull(expectedCategory.getId());
        Assertions.assertNotNull(expectedCategory.getCreatedAt());
        Assertions.assertNotNull(expectedCategory.getUpdatedAt());
        Assertions.assertNull(expectedCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, expectedCategory.getName());
        Assertions.assertEquals(expectedDescription, expectedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, expectedCategory.isActive());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveError() {

        final String expectedName = "Filmes";
        final int expectedErrorCount = 1;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;

        Category expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(expectedCategory);
        Assertions.assertNotNull(expectedCategory.getId());
        Assertions.assertNotNull(expectedCategory.getCreatedAt());
        Assertions.assertNotNull(expectedCategory.getUpdatedAt());
        Assertions.assertNotNull(expectedCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, expectedCategory.getName());
        Assertions.assertEquals(expectedDescription, expectedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, expectedCategory.isActive());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() {

        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;

        Category expectedCategory = Category.newCategory(expectedName, expectedDescription, true);

        final Instant createAt = expectedCategory.getCreatedAt();
        final Instant updatedAt = expectedCategory.getUpdatedAt();

        Assertions.assertTrue(expectedCategory.isActive());
        Assertions.assertNull(expectedCategory.getDeletedAt());

        final Category actualCategory = expectedCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(createAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, expectedCategory.getName());
        Assertions.assertEquals(expectedDescription, expectedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, expectedCategory.isActive());
    }

    @Test
    public void givenAValidInactivateCategory_whenCallActivate_thenReturnCategoryActivated() {

        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;

        Category expectedCategory = Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        final Instant createAt = expectedCategory.getCreatedAt();
        final Instant updatedAt = expectedCategory.getUpdatedAt();

        Assertions.assertFalse(expectedCategory.isActive());
        Assertions.assertNotNull(expectedCategory.getDeletedAt());

        final Category actualCategory = expectedCategory.activate();

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(createAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, expectedCategory.getName());
        Assertions.assertEquals(expectedDescription, expectedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, expectedCategory.isActive());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {

        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;

        Category expectedCategory = Category.newCategory("Docum", "Categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        final Instant createAt = expectedCategory.getCreatedAt();
        final Instant updatedAt = expectedCategory.getUpdatedAt();

        Assertions.assertTrue(expectedCategory.isActive());
        Assertions.assertNull(expectedCategory.getDeletedAt());

        final Category actualCategory = expectedCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(createAt, expectedCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {

        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;

        Category expectedCategory = Category.newCategory("Docum", "Categoria", true);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        final Instant createAt = expectedCategory.getCreatedAt();
        final Instant updatedAt = expectedCategory.getUpdatedAt();

        Assertions.assertTrue(expectedCategory.isActive());
        Assertions.assertNull(expectedCategory.getDeletedAt());

        final Category actualCategory = expectedCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(createAt, expectedCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() {

        final String expectedName = null;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;

        Category expectedCategory = Category.newCategory("Documentario", "Categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> expectedCategory.validate(new ThrowsValidationHandler()));

        final Instant createAt = expectedCategory.getCreatedAt();
        final Instant updatedAt = expectedCategory.getUpdatedAt();

        final Category actualCategory = expectedCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(createAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(expectedCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(expectedCategory.isActive());
    }
}
