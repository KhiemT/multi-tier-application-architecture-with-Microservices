package com.mycompany.app.idgenerator.utils;
import org.springframework.http.HttpStatus;

public class RestError {
	private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public RestError(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
