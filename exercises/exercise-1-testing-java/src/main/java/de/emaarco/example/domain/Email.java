package de.emaarco.example.domain;

public record Email(String value) {
    public Email {
        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
