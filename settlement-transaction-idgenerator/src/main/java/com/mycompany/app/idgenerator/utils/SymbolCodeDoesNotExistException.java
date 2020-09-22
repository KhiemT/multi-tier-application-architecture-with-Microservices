package com.mycompany.app.idgenerator.utils;

public class SymbolCodeDoesNotExistException extends RuntimeException{

    private static final long serialVersionUID = 7996516744853733268L;

    private static final String MESSAGE_FORMAT = "SymbolCode '%s' does not exist";

    private String symbolCode;

    public SymbolCodeDoesNotExistException(String symbolCode) {
        super(String.format(MESSAGE_FORMAT, symbolCode));

        this.symbolCode = symbolCode;
    }

	public String getSymbolCode() {
		return symbolCode;
	}

    
}
