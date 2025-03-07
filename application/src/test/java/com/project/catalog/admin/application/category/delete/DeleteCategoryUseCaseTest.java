package com.project.catalog.admin.application.category.delete;

import com.project.catalog.admin.application.UseCaseTest;
import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {

        final Category category = Category.newCategory("Filme", "Categoria dos Filmes", true);
        final CategoryID categoryId = category.getId();

        doNothing().when(categoryGateway).deleteById(eq(categoryId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(categoryId.getValue()));

        verify(categoryGateway, times(1)).deleteById(categoryId);
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK() {

        final Category category = Category.newCategory("Filme", "Categoria dos Filmes", true);
        final CategoryID categoryId = CategoryID.from("007");

        doNothing().when(categoryGateway).deleteById(categoryId);

        Assertions.assertDoesNotThrow(() -> useCase.execute(categoryId.getValue()));

        verify(categoryGateway, times(1)).deleteById(categoryId);
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {

        final Category category = Category.newCategory("Filme", "Categoria dos Filmes", true);
        final CategoryID categoryId = category.getId();

        doThrow(new IllegalStateException("Gateway error")).when(categoryGateway).deleteById(eq(categoryId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(categoryId.getValue()));

        verify(categoryGateway, times(1)).deleteById(eq(categoryId));
    }
}
