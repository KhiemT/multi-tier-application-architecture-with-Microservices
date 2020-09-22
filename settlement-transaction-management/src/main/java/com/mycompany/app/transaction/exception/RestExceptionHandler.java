package com.mycompany.app.transaction.exception;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Special Controller for handling validation errors in the REST API requests.
 * Very much based on this article: http://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();

        List<String> errors = Stream.concat(
                fieldErrors.stream().map(f -> f.getField() + ", " + f.getDefaultMessage()),
                globalErrors.stream().map(f -> f.getObjectName() + ", " + f.getDefaultMessage())
        ).collect(toList());

        ErrorDTO errorDTO = RestUtils.createErrorDTOFromRestError(CommonRestErrors.GENERAL_METHOD_ARGUMENT_NOT_VALID,
                ImmutableMap.of("fieldErrors", fieldErrors, "globalErrors", globalErrors, "errors", errors));
        return new ResponseEntity<>(errorDTO, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        ErrorDTO errorDTO = RestUtils.createErrorDTOFromRestError(CommonRestErrors.GENERAL_MEDIA_TYPE_NOT_SUPPORTED,
                ImmutableMap.of("unsupportedContentType", String.valueOf(ex.getContentType()), "supportedContentType", MediaType.toString(ex.getSupportedMediaTypes())));

        return new ResponseEntity<>(errorDTO, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(RestUtils.createErrorDTOFromRestError(CommonRestErrors.GENERAL_MEDIA_TYPE_NOT_SUPPORTED), headers, status);
    }

    @ExceptionHandler(RestException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleRestException(RestException ex) {
        LOGGER.error("Handling rest exception", ex);
        return new ResponseEntity<>(RestUtils.createErrorDTOFromRestErrorWithParameters(ex.toRestError(), ex.getErrorParams()), ex.toRestError().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        LOGGER.error("Handling exception", ex);
        return new ResponseEntity<>(RestUtils.createErrorDTOFromRestError(CommonRestErrors.GENERAL_SERVICE_ERROR), CommonRestErrors.GENERAL_SERVICE_ERROR.getHttpStatus());
    }

    @ExceptionHandler(GatewayException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleGatewayException(GatewayException ex) {
        LOGGER.error("Handling gateway exception", ex);
        return new ResponseEntity<>(ex.getErrorDTO(), ex.getHttpStatus());
    }


}

