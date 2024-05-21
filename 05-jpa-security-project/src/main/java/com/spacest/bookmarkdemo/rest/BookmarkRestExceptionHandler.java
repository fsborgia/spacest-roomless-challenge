package com.spacest.bookmarkdemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookmarkRestExceptionHandler {
    //exception handler for non-valid index
    @ExceptionHandler
    public ResponseEntity<BookmarkErrorResponse> handleNotFoundException(BookmarkNotFoundException e) {
        BookmarkErrorResponse error = new BookmarkErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BookmarkErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        BookmarkErrorResponse error = new BookmarkErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //add an exception handler for generic exception
    @ExceptionHandler
    public ResponseEntity<BookmarkErrorResponse> handleGenericException(Exception e) {
        BookmarkErrorResponse error = new BookmarkErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
