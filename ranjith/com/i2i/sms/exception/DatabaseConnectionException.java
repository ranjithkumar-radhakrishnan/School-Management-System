package com.i2i.sms.exception;

import java.lang.Exception;

/**
*
* Class which is responsible for custom Exception.
* 
*/

public class DatabaseConnectionException extends RuntimeException{

    public DatabaseConnectionException(String message, Throwable e) {
        super(message, e);
    }


}