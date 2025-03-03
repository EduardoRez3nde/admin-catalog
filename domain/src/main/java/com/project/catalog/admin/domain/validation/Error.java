package com.project.catalog.admin.domain.validation;

public record Error(String message) {

    public static Error from(final String format, final Object ...args) {
        return new Error(String.format(format, args));
    }
}
