package com.project.catalog.admin.application.category.create;

import com.project.catalog.admin.application.UseCaseTest;
import com.project.catalog.admin.domain.category.CategoryGateway;
import com.project.catalog.admin.domain.validation.handler.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class CreateCategoryUseCaseTest extends UseCaseTest {

    /*
     TODO
      1. Teste do caminho feliz
      2. Teste passando uma propriedade inválida (name)
      3. Teste criando uma categoria inativa
      4. Teste simulando um erro generico vindo do gateway
    */


    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {

        final String expectedName = "Filmes";
        final String expectedDescription = "A Categoria mais assistida";
        final boolean expectedIsActive = true;

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(answer -> answer.getArgument(0));

        final CreateCategoryOutput actualOutput = useCase.execute(command).get();
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).create(argThat(category ->
            Objects.equals(expectedName, category.getName())
                && Objects.equals(expectedDescription, category.getDescription())
                && Objects.equals(expectedIsActive, category.isActive())
                && Objects.nonNull(category.getId())
                && Objects.nonNull(category.getCreatedAt())
                && Objects.nonNull(category.getUpdatedAt())
                && Objects.isNull(category.getDeletedAt())
        ));
    }

    @Test
    void givenInvalidName_whenCallsCreateCategory_shouldReturnDomainException() {

        final String expectedName = null;
        final String expectedDescription = "Descrição da Categoria";
        final boolean expectedIsActive = true;
        final String expectedErrorMessage = "'name' should not be null";
        final int expectedErrorCount = 1;

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final Notification notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    void giveAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {

        final String expectedName = "Filmes";
        final String expectedDescription = "Descrição da Categoria";
        final boolean expectedIsActive = false;

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(answer -> answer.getArgument(0));

        final CreateCategoryOutput actualOutput = useCase.execute(command).get();
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).create(argThat(category ->
            Objects.nonNull(category.getId())
                && Objects.equals(expectedName, category.getName())
                && Objects.equals(expectedDescription, category.getDescription())
                && Objects.equals(expectedIsActive, category.isActive())
                && Objects.nonNull(category.getCreatedAt())
                && Objects.nonNull(category.getUpdatedAt())
                && Objects.nonNull(category.getDeletedAt())
        ));
    }

    @Test
    void giveAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {

        final String expectedName = "Filmes";
        final String expectedDescription = "A Categoria mais assistida";
        final boolean expectedIsActive = true;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "Gateway error";

        final CreateCategoryCommand command =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final Notification notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.nonNull(category.getId())
                        && Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.isNull(category.getDeletedAt())
        ));
    }
}
