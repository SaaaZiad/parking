package com.example.parking.exception;

public class NotEnoughMoney extends RuntimeException{
  public NotEnoughMoney(String message){
    super(message);
  }
}
