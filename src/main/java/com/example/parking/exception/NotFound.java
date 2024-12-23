package com.example.parking.exception;

public class NotFound extends RuntimeException{
  public NotFound(String message){
    super(message);
  }
}
