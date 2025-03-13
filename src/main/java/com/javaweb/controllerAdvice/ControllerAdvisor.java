package com.javaweb.controllerAdvice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.model.ErrorResponseDTO;

import customexception.FieldRequiredException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ArithmeticException.class)

    public ResponseEntity<Object> handleArithmeticException(
    		ArithmeticException ex,  WebRequest request) {

    	ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
    	errorResponseDTO.setError(ex.getMessage());
    	List<String> details = new ArrayList<>();
    	details.add("Số nguyên không chia được cho 0");
    	errorResponseDTO.setDetail(details);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(FieldRequiredException.class)
    public ResponseEntity<Object> handleFieldRequiredException( 
    		FieldRequiredException ex,  WebRequest request) {

    	ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
    	errorResponseDTO.setError(ex.getMessage());
    	List<String> details = new ArrayList<>();
    	details.add("Check lại name hoặc numberOfBasement đi bởi vì đang bị null đó!");
    	errorResponseDTO.setDetail(details);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_GATEWAY);
    } 
}
