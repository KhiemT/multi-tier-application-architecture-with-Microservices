package com.mycompany.app.transaction.exception;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class RestUtils {

    private RestUtils() {
        // utility class
    }

    public static ErrorDTO createErrorDTOFromRestError(TranslatableToRestError restError) {
        return createErrorDTOFromRestErrorWithParameters(restError, Collections.emptyMap());
    }

    public static ErrorDTO createErrorDTOFromRestErrorWithParameters(TranslatableToRestError restError, Map<String, Object> params) {
        return createErrorDTOFromRestErrorWithParameters(restError.toRestError(), params);
    }

    public static ErrorDTO createErrorDTOFromRestErrorWithParameters(RestError restError, Map<String, Object> params) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setErrorCode(restError.getErrorCode());
        errorDTO.setErrorMessage(restError.getErrorMessage());
        Optional.ofNullable(params).filter(m -> !m.isEmpty()).ifPresent(errorDTO::setParams);
        return errorDTO;
    }

    public static ErrorDTO createErrorDTOFromRestError(TranslatableToRestError restError, Map<String, Object> params) {
        return createErrorDTOFromRestErrorWithParameters(restError, params);
    }

}
