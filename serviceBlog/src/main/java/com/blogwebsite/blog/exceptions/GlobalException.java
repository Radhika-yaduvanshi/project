//package com.blogwebsite.blog.exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalException {
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> GeneralException(Exception ex){
//        ErrorResponse errorResponse=new ErrorResponse("Error "+ex.getMessage(),"101");
//        return  new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//}
