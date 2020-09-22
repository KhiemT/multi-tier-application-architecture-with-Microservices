package com.mycompany.app.idgenerator.api.dto;

import java.util.HashMap;
import java.util.Map;

public class ErrorDTO extends DTO{
	private String errorCode;
    private String errorMessage;
    private Map<String, Object> params = new HashMap();

    public ErrorDTO() {
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
