package com.i2i.sms.exception;

/**
* Class which is responsible for custom Exception.
*/
public class StudentException extends RuntimeException {
    public StudentException(String message) {
        super(message);
    }
}