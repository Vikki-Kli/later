package org.example.exception;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(String s){
        super(s);
    }
}
