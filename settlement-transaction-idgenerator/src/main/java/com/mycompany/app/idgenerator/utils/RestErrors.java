package com.mycompany.app.idgenerator.utils;

import org.springframework.http.HttpStatus;

public enum RestErrors implements TranslatableToRestError {


        ENTITY_TYPE_CODE_NOT_FOUND("ENTITY TYPE CODE NOT FOUND", HttpStatus.NOT_FOUND);


    RestErrors(String errorMessage, HttpStatus httpStatus){
            this.httpStatus=httpStatus;
            this.errorMessage = errorMessage;
        }

        private String errorMessage;

        private HttpStatus httpStatus;

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public RestError toRestError(){
            return new RestError(this.name(),errorMessage,httpStatus);
        }
    }

