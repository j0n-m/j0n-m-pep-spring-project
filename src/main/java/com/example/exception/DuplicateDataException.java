package com.example.exception;

public class DuplicateDataException extends RuntimeException{
    public DuplicateDataException(){
        super();
    }
    public DuplicateDataException(String message){
        super(message);
    }
}
