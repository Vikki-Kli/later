package org.example.exception;

public class NoSuchUserException extends RuntimeException{
    NoSuchUserException(String s){
        super(s);
    }
}
