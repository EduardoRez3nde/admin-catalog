package com.project.catalog.admin.application.category.update;

import com.project.catalog.admin.application.UseCaseTest;
import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.category.CategoryID;
import com.project.catalog.admin.domain.exceptions.DomainException;
import com.project.catalog.admin.domain.validation.handler.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UpdateCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {

        Category aCategory = Category.newCategory("Documentario", "Descrição do Documentario", true);

        final String expectedName = "Filmes";
        final String expectedDescription = "Descrição do Filme";
        final boolean expectedIsActive = true;
        final CategoryID categoryId = aCategory.getId();

        final UpdateCategoryCommand command = UpdateCategoryCommand.with(
                categoryId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(categoryId))).thenReturn(Optional.of(Category.with(Category.deepCopy(aCategory))));

        when(categoryGateway.update(any())).thenAnswer(answer -> answer.getArgument(0));

        final UpdateCategoryOutput actualOutput = useCase.execute(command).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());
    
        Mockito.verify(categoryGateway, times(1)).update(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.equals(categoryId, category.getId())
                        && Objects.equals(aCategory.getCreatedAt(), category.getCreatedAt())
                        && aCategory.getUpdatedAt().isBefore(category.getUpdatedAt())
                        && Objects.isNull(category.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {

        Category category = Category.newCategory("Filme", "Descrição da Categoria", true);

        final String expectedName = null;
        final String expectedDescription = "Descrição do Filme";
        final boolean expectedIsActive = true;
        final CategoryID expectedId = category.getId();

        final String errorMessage = "'name' should not be null";
        final int errorCount = 1;

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.with(category)));

        final Notification notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(errorCount, notification.getErrors().size());
        Assertions.assertEquals(errorMessage, notification.firstError().message());

        verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {

        Category category = Category.newCategory("Filme", "Descrição da categoria", true);

        final String expectedName = "Documentario";
        final String expectedDescription = "Descrição do Filme";
        final boolean expectedIsActive = false;
        final CategoryID categoryId = category.getId();

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(categoryId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(categoryId))).thenReturn(Optional.of(Category.with(category)));
        when(categoryGateway.update(any())).thenAnswer(answer -> answer.getArgument(0));

        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());

        final UpdateCategoryOutput output = useCase.execute(command).get();

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        verify(categoryGateway, times(1)).findById(eq(categoryId));
        verify(categoryGateway, times(1)).update(argThat(updateCategory ->
                Objects.equals(expectedName, updateCategory.getName())
                        && Objects.equals(expectedDescription, updateCategory.getDescription())
                        && Objects.equals(expectedIsActive, updateCategory.isActive())
                        && Objects.equals(categoryId, updateCategory.getId())
                        && Objects.equals(category.getCreatedAt(), updateCategory.getCreatedAt())
                        && category.getUpdatedAt().isBefore(updateCategory.getUpdatedAt())
                        && Objects.nonNull(updateCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {

        Category category = Category.newCategory("Filme", "Descrição da Categoria", true);

        final String expectedName = "Filmes";
        final String expectedDescription = "Descrição do Filme";
        final boolean expectedIsActive = true;
        final CategoryID categoryId = category.getId();
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "Gateway error";

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(categoryId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(categoryId))).thenReturn(Optional.of(Category.with(category)));
        when(categoryGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final Notification notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(categoryGateway, times(1)).update(argThat(updateCategory ->
                Objects.equals(expectedName, updateCategory.getName())
                        && Objects.equals(expectedDescription, updateCategory.getDescription())
                        && Objects.equals(expectedIsActive, updateCategory.isActive())
                        && Objects.equals(categoryId, updateCategory.getId())
                        && Objects.equals(category.getCreatedAt(), updateCategory.getCreatedAt())
                        && category.getUpdatedAt().isBefore(updateCategory.getUpdatedAt())
                        && Objects.isNull(updateCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {

        final String expectedName = "Filmes";
        final String expectedDescription = "Descrição do Filme";
        final boolean expectedIsActive = false;
        final String categoryId = "007";
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "Category with ID 007, was not found.";

        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(categoryId, expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(CategoryID.from(categoryId)))).thenReturn(Optional.empty());

        final DomainException exception =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(command));

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        verify(categoryGateway, times(1)).findById(eq(CategoryID.from(categoryId)));
        verify(categoryGateway, times(0)).update(any());
    }
}
