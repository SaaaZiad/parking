package com.example.parking.exception;

public class NotEnoughTickets extends RuntimeException {
  public NotEnoughTickets(String message){
    super(message);
  }
}
