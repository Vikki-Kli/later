package org.example.exception;

public class NoSuchItemException extends RuntimeException{
    public NoSuchItemException(String s){
        super(s);
    }
}
