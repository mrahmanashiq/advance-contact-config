package com.mra.controller;

import com.mra.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/i18n")
@RequiredArgsConstructor
@Tag(name = "Internationalization", description = "API for managing internationalization messages")
public class InternationalizationController {

    private final MessageService messageService;
    private final MessageSource messageSource;

    @GetMapping("/messages")
    @Operation(summary = "Get all messages for a specific locale", 
               description = "Returns all translation messages for the specified locale. Perfect for Vue frontend.")
    public ResponseEntity<Map<String, String>> getMessages(
            @Parameter(description = "Language code (e.g., en, fr, bn, ar)", example = "en")
            @RequestParam(defaultValue = "en") String lang) {
        
        Locale locale = Locale.forLanguageTag(lang);
        Map<String, String> messages = getAllMessages(locale);
        
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/message/{key}")
    @Operation(summary = "Get specific message by key", 
               description = "Returns a specific translation message by key for the specified locale")
    public ResponseEntity<Map<String, String>> getMessage(
            @Parameter(description = "Message key", example = "success.customer.created")
            @PathVariable String key,
            @Parameter(description = "Language code", example = "en")
            @RequestParam(defaultValue = "en") String lang) {
        
        Locale locale = Locale.forLanguageTag(lang);
        String message = messageService.getMessage(key, locale);
        
        Map<String, String> response = new HashMap<>();
        response.put("key", key);
        response.put("message", message);
        response.put("language", lang);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/supported-locales")
    @Operation(summary = "Get supported locales", 
               description = "Returns a list of all supported language codes")
    public ResponseEntity<Map<String, Object>> getSupportedLocales() {
        Map<String, Object> locales = new HashMap<>();
        
        locales.put("en", Map.of("name", "English", "nativeName", "English"));
        locales.put("fr", Map.of("name", "French", "nativeName", "Français"));
        locales.put("bn", Map.of("name", "Bengali", "nativeName", "বাংলা"));
        locales.put("ar", Map.of("name", "Arabic", "nativeName", "العربية"));
        locales.put("es", Map.of("name", "Spanish", "nativeName", "Español"));
        locales.put("de", Map.of("name", "German", "nativeName", "Deutsch"));
        locales.put("zh", Map.of("name", "Chinese", "nativeName", "中文"));
        locales.put("hi", Map.of("name", "Hindi", "nativeName", "हिन्दी"));
        
        return ResponseEntity.ok(locales);
    }

    private Map<String, String> getAllMessages(Locale locale) {
        Map<String, String> messages = new HashMap<>();
        
        // Common message keys that your Vue app will need
        String[] messageKeys = {
            // General
            "app.name", "app.description", "app.version",
            
            // Success messages  
            "success.customer.created", "success.customer.updated", "success.customer.deleted",
            "success.customer.found", "success.customers.retrieved",
            
            // Error messages
            "error.customer.not.found", "error.customer.email.duplicate", 
            "error.customer.validation.failed", "error.customer.creation.failed",
            "error.customer.update.failed", "error.customer.deletion.failed",
            "error.database.connection", "error.internal.server", "error.access.denied",
            "error.unauthorized", "error.invalid.request", "error.invalid.format",
            "error.required.field", "error.invalid.email", "error.invalid.age",
            "error.invalid.name", "error.invalid.phone", "error.no.changes.detected",
            
            // Validation messages
            "validation.name.required", "validation.name.size", "validation.email.required",
            "validation.email.invalid", "validation.email.size", "validation.age.required",
            "validation.age.min", "validation.age.max", "validation.phone.invalid",
            "validation.phone.size", "validation.address.size",
            
            // Business logic
            "business.customer.status.active", "business.customer.status.inactive",
            "business.customer.status.suspended", "business.customer.age.minor",
            "business.customer.age.adult",
            
            // System messages
            "system.maintenance", "system.ready", "system.health.ok", "system.health.error"
        };
        
        for (String key : messageKeys) {
            try {
                String message = messageSource.getMessage(key, null, locale);
                messages.put(key, message);
            } catch (Exception e) {
                // If message not found, use the key itself
                messages.put(key, key);
            }
        }
        
        return messages;
    }
}