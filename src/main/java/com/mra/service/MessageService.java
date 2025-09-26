package com.mra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    /**
     * Get localized message with current locale
     */
    public String getMessage(String key) {
        return getMessage(key, null, LocaleContextHolder.getLocale());
    }

    /**
     * Get localized message with parameters and current locale
     */
    public String getMessage(String key, Object[] params) {
        return getMessage(key, params, LocaleContextHolder.getLocale());
    }

    /**
     * Get localized message with specific locale
     */
    public String getMessage(String key, Locale locale) {
        return getMessage(key, null, locale);
    }

    /**
     * Get localized message with parameters and specific locale
     */
    public String getMessage(String key, Object[] params, Locale locale) {
        try {
            return messageSource.getMessage(key, params, locale);
        } catch (Exception e) {
            // Fallback to key if message not found
            return key;
        }
    }

    /**
     * Get localized message with default value
     */
    public String getMessageOrDefault(String key, String defaultMessage) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }

    /**
     * Check if message key exists
     */
    public boolean messageExists(String key) {
        try {
            messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}