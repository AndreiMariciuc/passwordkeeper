package com.familypassword.utils;

import com.familypassword.dto.BodyDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {

    public static <T> ResponseEntity<?> sendOkResponse(String message, T obj) {
        return new ResponseEntity<>(BodyDto.of(message,
                                                  obj), HttpStatus.ACCEPTED);
    }

    public static <T> ResponseEntity<?> sendNotOkResponse(String message) {
        return new ResponseEntity<>(BodyDto.<T>of(message,
                                                  null), HttpStatus.NOT_ACCEPTABLE);
    }
}
