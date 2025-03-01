package com.project.catalog.admin.application;

import com.project.catalog.admin.domain.Identifier;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public abstract class UseCaseTest implements BeforeEachCallback {

    // reseta os mocks retornados pelo método getMocks()
    @Override
    public void beforeEach(final ExtensionContext context) {
        Mockito.reset(getMocks().toArray());
    }

    // retorna uma lista de mocks que serão resetados antes de cada teste.
    protected abstract List<Object> getMocks();

    // converte coleções de objetos Identifier em coleções de strings.
    protected Set<String> asString(final Set<? extends Identifier> ids) {
        return ids.stream().map(Identifier::getValue).collect(Collectors.toSet());
    }

    protected List<String> asString(final List<? extends Identifier> ids) {
        return ids.stream().map(Identifier::getValue).toList();
    }
}
