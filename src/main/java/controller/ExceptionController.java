package controller;

import com.fasterxml.jackson.core.JsonParseException;
import controller.ExceptionMessage.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import service.CustomExceptions.AccountNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> duplicateConstraints(AccountNotFoundException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.CONFLICT.toString(), exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> userValidationError(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        exception.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.info(errors.toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> forbidden(AccessDeniedException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.FORBIDDEN.toString(), exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorMessage> jsonParseFailure(JsonParseException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST.toString(), "Not valid json. " +  exception.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
