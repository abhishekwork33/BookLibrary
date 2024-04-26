package com.olik.booklibrary.error;

public class BookAlreadyRentedException extends RuntimeException{
    public BookAlreadyRentedException(String message){super(message);}

}
