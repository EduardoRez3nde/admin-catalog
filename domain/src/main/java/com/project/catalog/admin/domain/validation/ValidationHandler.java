package com.project.catalog.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error error);  // Adiciona um erro à lista de erros.

    ValidationHandler append(ValidationHandler handler); // Adiciona todos os erros de outro ValidationHandler

    <T> T validate(Validation<T> validation);   // Executa uma validação e retorna o resultado. Se houver erro, ele é tratado.

    List<Error> getErrors();

    default boolean hasError() {    // Verifica se há erros na lista.
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (hasError())
            return getErrors().getFirst();
        return null;
    }

    interface Validation<T> {
        T Validate();
    }
}
