package org.example.exception;

public class NoSuchItemException extends RuntimeException{
    NoSuchItemException(String s){
        super(s);
    }
}
