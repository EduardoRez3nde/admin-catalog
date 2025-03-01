package com.project.catalog.admin.domain.category;

import com.project.catalog.admin.domain.AggregateRoot;
import com.project.catalog.admin.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID id,
            final String name,
            final String description,
            final Boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String name, final String description, final Boolean active) {
        final CategoryID id = CategoryID.unique();
        final Instant deletedAt = active ? null : Instant.now();
        return new Category(id, name, description, active, Instant.now(), Instant.now(), deletedAt);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }


    public Instant getUpdatedAt() {
        return updatedAt;
    }


    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category deactivate() {
        if (isActive()) {
            active = false;
            updatedAt = Instant.now();
            deletedAt = Instant.now();
        }
        return this;
    }

    public Category activate() {
        if (!isActive()) {
            active = true;
            updatedAt = Instant.now();
            deletedAt = null;
        }
        return this;
    }

    public Category update(final String name, final String description, final boolean isActive) {
        if (isActive)
            activate();
        else
            deactivate();
        this.name = name;
        this.description = description;
        this.updatedAt = Instant.now();
        return this;
    }
}