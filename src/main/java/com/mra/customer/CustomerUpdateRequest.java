package com.mra.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}