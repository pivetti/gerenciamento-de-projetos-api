package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PatchFieldService {

    public String getString(Map<String, Object> updates, String field) {
        Object value = updates.get(field);
        return value != null ? value.toString() : null;
    }

    public Integer getInteger(Map<String, Object> updates, String field) {
        Object value = updates.get(field);
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        return Integer.valueOf(value.toString());
    }

    public Long getLong(Map<String, Object> updates, String field) {
        Object value = updates.get(field);
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.valueOf(value.toString());
    }

    public BigDecimal getBigDecimal(Map<String, Object> updates, String field) {
        Object value = updates.get(field);
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        return new BigDecimal(value.toString());
    }

    public Boolean getBoolean(Map<String, Object> updates, String field) {
        Object value = updates.get(field);
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean bool) {
            return bool;
        }
        return Boolean.valueOf(value.toString());
    }

    public LocalDate getLocalDate(Map<String, Object> updates, String field) {
        Object value = updates.get(field);
        if (value == null) {
            return null;
        }
        return LocalDate.parse(value.toString());
    }

    public <E extends Enum<E>> E getEnum(Map<String, Object> updates, String field, Class<E> enumClass) {
        Object value = updates.get(field);
        if (value == null) {
            return null;
        }
        return Enum.valueOf(enumClass, value.toString());
    }
}
