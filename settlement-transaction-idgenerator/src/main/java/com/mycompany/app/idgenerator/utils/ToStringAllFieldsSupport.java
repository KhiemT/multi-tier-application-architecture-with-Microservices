package com.mycompany.app.idgenerator.utils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class ToStringAllFieldsSupport {
    private static final String[] EXCLUDED_FIELD_NAMES_FROM_TOSTRING = new String[]{"password"};

    public ToStringAllFieldsSupport() {
    }

    public String toString() {
        return (new ReflectionToStringBuilder(this)).setExcludeFieldNames(EXCLUDED_FIELD_NAMES_FROM_TOSTRING).toString();
    }
}
