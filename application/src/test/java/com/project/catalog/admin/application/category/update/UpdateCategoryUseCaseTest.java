package com.project.catalog.admin.application.category.update;

import com.project.catalog.admin.application.UseCaseTest;
import com.project.catalog.admin.domain.category.Category;
import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.category.CategoryID;
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
}
