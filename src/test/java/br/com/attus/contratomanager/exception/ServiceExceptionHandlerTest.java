package br.com.attus.contratomanager.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceExceptionHandlerTest {

    private ServiceExceptionHandler serviceExceptionHandler;

    @BeforeEach
    void setUp() {
        serviceExceptionHandler = new ServiceExceptionHandler();
    }

    @Test
    void testServiceException() {
        ServiceException serviceException = new ServiceException(HttpStatus.BAD_REQUEST, "Test ServiceException");

        ResponseEntity<Map<String, Object>> responseEntity = serviceExceptionHandler.serviceException(serviceException);

        Map<String, Object> response = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.get("status"));
        assertEquals("Bad Request", response.get("error"));
        assertEquals("Test ServiceException", response.get("message"));
    }

    @Test
    void testMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("object", "field", "Field is required");

        when(bindingResult.getFieldErrors()).thenReturn(java.util.Collections.singletonList(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, Object>> responseEntity = serviceExceptionHandler.methodArgumentNotValidException(ex);

        Map<String, Object> response = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.get("status"));
        assertEquals("Bad Request", response.get("error"));
        assertEquals("Validation failed for object", response.get("message"));

        Map<String, String> errors = (Map<String, String>) response.get("errors");
        assertEquals(1, errors.size());
        assertEquals("Field is required", errors.get("field"));
    }
}