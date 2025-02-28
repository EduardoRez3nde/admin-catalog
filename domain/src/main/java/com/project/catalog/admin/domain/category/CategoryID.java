package com.project.catalog.admin.domain.category;

import com.project.catalog.admin.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {

    private final String id;

    private CategoryID(final String id) {
        Objects.requireNonNull(id, "Id cannot be null");
        this.id = id;
    }

    public static CategoryID unique() {
        return CategoryID.from(UUID.randomUUID());
    }

    public static CategoryID from (String id) {
        return new CategoryID(id);
    }

    public static CategoryID from (UUID id) {
        return new CategoryID(id.toString().toLowerCase());
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
