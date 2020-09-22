package com.mycompany.app.transaction.exception;

import java.util.Map;

public class RestException extends RuntimeException implements TranslatableToRestError {

    private final RestError restError;

    @Override
    public RestError toRestError() {
        return restError;
    }

    private Map<String, Object> errorParams;

    public RestException(RestError restError, Map<String, Object> params) {
        this(restError);
        this.errorParams = params;
    }

    public RestException(RestError restError) {
        super(restError.getErrorMessage());
        this.restError = restError;
    }

    public RestException(TranslatableToRestError restError, Map<String, Object> params) {
        this(restError.toRestError());
        this.errorParams = params;
    }

    public RestException(TranslatableToRestError restError) {
        this(restError.toRestError());
    }

    public Map<String, Object> getErrorParams() {
        return errorParams;
    }

}
