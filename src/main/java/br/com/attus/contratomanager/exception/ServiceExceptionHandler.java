package br.com.attus.contratomanager.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler{
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<String> ServiceException(ServiceException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
