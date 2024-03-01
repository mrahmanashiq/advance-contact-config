package com.mra.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
