package com.mycompany.app.idgenerator.api.dto;

/**
 * Properties of DTO have to be an Object
 *
 */
public class TransactionIdDTO extends DTO {

	private String code;

    public TransactionIdDTO(String code) {
        this.code = code;
    }

    public TransactionIdDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
