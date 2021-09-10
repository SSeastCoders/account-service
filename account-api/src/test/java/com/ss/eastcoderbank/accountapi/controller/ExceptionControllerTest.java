package com.ss.eastcoderbank.accountapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.ss.eastcoderbank.core.exeception.AccountNotEmptyException;
import com.ss.eastcoderbank.core.exeception.AccountNotFoundException;
import com.ss.eastcoderbank.core.exeception.UserNotFoundException;
import com.ss.eastcoderbank.core.exeception.response.ErrorMessage;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ExceptionControllerTest {
    @Test
    public void testUserValidationError() {
        ExceptionController exceptionController = new ExceptionController();
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(new ArrayList<FieldError>());
        ResponseEntity<Map<String, String>> actualUserValidationErrorResult = exceptionController
                .userValidationError(methodArgumentNotValidException);
        assertEquals(2, actualUserValidationErrorResult.getBody().size());
        assertEquals("<400 BAD_REQUEST Bad Request,{message=, status=400 BAD_REQUEST},[]>",
                actualUserValidationErrorResult.toString());
        assertTrue(actualUserValidationErrorResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualUserValidationErrorResult.getStatusCode());
        assertTrue(actualUserValidationErrorResult.getHeaders().isEmpty());
        verify(methodArgumentNotValidException).getFieldErrors();
    }

    @Test
    public void testUserValidationError2() {
        ExceptionController exceptionController = new ExceptionController();

        ArrayList<FieldError> fieldErrorList = new ArrayList<FieldError>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrorList);
        ResponseEntity<Map<String, String>> actualUserValidationErrorResult = exceptionController
                .userValidationError(methodArgumentNotValidException);
        assertEquals(3, actualUserValidationErrorResult.getBody().size());
        assertEquals("<400 BAD_REQUEST Bad Request,{Field=Default Message, message= Default Message, status=400"
                + " BAD_REQUEST},[]>", actualUserValidationErrorResult.toString());
        assertTrue(actualUserValidationErrorResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualUserValidationErrorResult.getStatusCode());
        assertTrue(actualUserValidationErrorResult.getHeaders().isEmpty());
        verify(methodArgumentNotValidException).getFieldErrors();
    }

    @Test
    public void testUserValidationError3() {
        ExceptionController exceptionController = new ExceptionController();

        ArrayList<FieldError> fieldErrorList = new ArrayList<FieldError>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrorList);
        ResponseEntity<Map<String, String>> actualUserValidationErrorResult = exceptionController
                .userValidationError(methodArgumentNotValidException);
        assertEquals(3, actualUserValidationErrorResult.getBody().size());
        assertEquals(
                "<400 BAD_REQUEST Bad Request,{Field=Default Message, message= Default Message Default Message, status=400"
                        + " BAD_REQUEST},[]>",
                actualUserValidationErrorResult.toString());
        assertTrue(actualUserValidationErrorResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualUserValidationErrorResult.getStatusCode());
        assertTrue(actualUserValidationErrorResult.getHeaders().isEmpty());
        verify(methodArgumentNotValidException).getFieldErrors();
    }

    @Test
    public void testUserValidationError4() {
        ExceptionController exceptionController = new ExceptionController();
        FieldError fieldError = mock(FieldError.class);
        when(fieldError.getDefaultMessage()).thenThrow(new AccountNotFoundException("An error occurred"));
        when(fieldError.getField()).thenReturn("foo");

        ArrayList<FieldError> fieldErrorList = new ArrayList<FieldError>();
        fieldErrorList.add(fieldError);
        fieldErrorList.add(null);
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrorList);
        assertThrows(AccountNotFoundException.class,
                () -> exceptionController.userValidationError(methodArgumentNotValidException));
        verify(methodArgumentNotValidException).getFieldErrors();
        verify(fieldError).getDefaultMessage();
        verify(fieldError).getField();
    }

    @Test
    public void testForbidden() {
        ExceptionController exceptionController = new ExceptionController();
        ResponseEntity<ErrorMessage> actualForbiddenResult = exceptionController
                .forbidden(new AccessDeniedException("Msg"));
        assertTrue(actualForbiddenResult.getHeaders().isEmpty());
        assertTrue(actualForbiddenResult.hasBody());
        assertEquals(HttpStatus.FORBIDDEN, actualForbiddenResult.getStatusCode());
        ErrorMessage body = actualForbiddenResult.getBody();
        assertEquals("403 FORBIDDEN", body.getStatus());
        assertEquals("Msg", body.getMessage());
    }

    @Test
    public void testForbidden2() {
        ExceptionController exceptionController = new ExceptionController();

        AccessDeniedException accessDeniedException = new AccessDeniedException("Msg");
        accessDeniedException.addSuppressed(new Throwable());
        ResponseEntity<ErrorMessage> actualForbiddenResult = exceptionController.forbidden(accessDeniedException);
        assertTrue(actualForbiddenResult.getHeaders().isEmpty());
        assertTrue(actualForbiddenResult.hasBody());
        assertEquals(HttpStatus.FORBIDDEN, actualForbiddenResult.getStatusCode());
        ErrorMessage body = actualForbiddenResult.getBody();
        assertEquals("403 FORBIDDEN", body.getStatus());
        assertEquals("Msg", body.getMessage());
    }

    @Test
    public void testJsonParseFailure() {
        ExceptionController exceptionController = new ExceptionController();
        ResponseEntity<ErrorMessage> actualJsonParseFailureResult = exceptionController
                .jsonParseFailure(new JsonParseException("Msg", new JsonLocation("Src Ref", 1L, 2, 1)));
        assertTrue(actualJsonParseFailureResult.getHeaders().isEmpty());
        assertTrue(actualJsonParseFailureResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualJsonParseFailureResult.getStatusCode());
        ErrorMessage body = actualJsonParseFailureResult.getBody();
        assertEquals("400 BAD_REQUEST", body.getStatus());
        assertEquals("Not valid json. Msg\n at [Source: (String)\"Src Ref\"; line: 2, column: 1]", body.getMessage());
    }

    @Test
    public void testNoUserFound() {
        ExceptionController exceptionController = new ExceptionController();
        ResponseEntity<ErrorMessage> actualNoUserFoundResult = exceptionController
                .noUserFound(new UserNotFoundException("An error occurred"));
        assertTrue(actualNoUserFoundResult.getHeaders().isEmpty());
        assertTrue(actualNoUserFoundResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualNoUserFoundResult.getStatusCode());
        ErrorMessage body = actualNoUserFoundResult.getBody();
        assertEquals("404 NOT_FOUND", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }

    @Test
    public void testNoUserFound2() {
        ExceptionController exceptionController = new ExceptionController();

        UserNotFoundException userNotFoundException = new UserNotFoundException("An error occurred");
        userNotFoundException.addSuppressed(new Throwable());
        ResponseEntity<ErrorMessage> actualNoUserFoundResult = exceptionController.noUserFound(userNotFoundException);
        assertTrue(actualNoUserFoundResult.getHeaders().isEmpty());
        assertTrue(actualNoUserFoundResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualNoUserFoundResult.getStatusCode());
        ErrorMessage body = actualNoUserFoundResult.getBody();
        assertEquals("404 NOT_FOUND", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }

    @Test
    public void testDuplicateConstraints() {
        ExceptionController exceptionController = new ExceptionController();
        ResponseEntity<ErrorMessage> actualDuplicateConstraintsResult = exceptionController
                .duplicateConstraints(new AccountNotEmptyException("An error occurred"));
        assertTrue(actualDuplicateConstraintsResult.getHeaders().isEmpty());
        assertTrue(actualDuplicateConstraintsResult.hasBody());
        assertEquals(HttpStatus.PRECONDITION_FAILED, actualDuplicateConstraintsResult.getStatusCode());
        ErrorMessage body = actualDuplicateConstraintsResult.getBody();
        assertEquals("412 PRECONDITION_FAILED", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }

    @Test
    public void testDuplicateConstraints2() {
        ExceptionController exceptionController = new ExceptionController();

        AccountNotEmptyException accountNotEmptyException = new AccountNotEmptyException("An error occurred");
        accountNotEmptyException.addSuppressed(new Throwable());
        ResponseEntity<ErrorMessage> actualDuplicateConstraintsResult = exceptionController
                .duplicateConstraints(accountNotEmptyException);
        assertTrue(actualDuplicateConstraintsResult.getHeaders().isEmpty());
        assertTrue(actualDuplicateConstraintsResult.hasBody());
        assertEquals(HttpStatus.PRECONDITION_FAILED, actualDuplicateConstraintsResult.getStatusCode());
        ErrorMessage body = actualDuplicateConstraintsResult.getBody();
        assertEquals("412 PRECONDITION_FAILED", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }

    @Test
    public void testDuplicateConstraints3() {
        ExceptionController exceptionController = new ExceptionController();
        ResponseEntity<ErrorMessage> actualDuplicateConstraintsResult = exceptionController
                .duplicateConstraints(new AccountNotFoundException("An error occurred"));
        assertTrue(actualDuplicateConstraintsResult.getHeaders().isEmpty());
        assertTrue(actualDuplicateConstraintsResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualDuplicateConstraintsResult.getStatusCode());
        ErrorMessage body = actualDuplicateConstraintsResult.getBody();
        assertEquals("404 NOT_FOUND", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }

    @Test
    public void testDuplicateConstraints4() {
        ExceptionController exceptionController = new ExceptionController();

        AccountNotFoundException accountNotFoundException = new AccountNotFoundException("An error occurred");
        accountNotFoundException.addSuppressed(new Throwable());
        ResponseEntity<ErrorMessage> actualDuplicateConstraintsResult = exceptionController
                .duplicateConstraints(accountNotFoundException);
        assertTrue(actualDuplicateConstraintsResult.getHeaders().isEmpty());
        assertTrue(actualDuplicateConstraintsResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualDuplicateConstraintsResult.getStatusCode());
        ErrorMessage body = actualDuplicateConstraintsResult.getBody();
        assertEquals("404 NOT_FOUND", body.getStatus());
        assertEquals("An error occurred", body.getMessage());
    }
}

