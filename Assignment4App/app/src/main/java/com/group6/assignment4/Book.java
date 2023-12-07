/*
  Filename: Book.java
  Student's Name: Bittu Patel
  Student ID: 200556453
  Date: December 06, 2023
*/
package com.group6.assignment4;

import java.io.Serializable;

public class Book implements Serializable {

    private String _id;
    private String name;
    private String isbn;
    private Integer rating;
    private String author;
    private String publisher;

    public Book() {
        this._id = _id;
        this.name = name;
        this.isbn = isbn;
        this.rating = rating;
        this.author = author;
        this.publisher = publisher;
    }

    public String getID() {
        return _id;
    }

    public void setID(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public Number getRating() {
        return rating;
    }

    public void setRating(Number rating) { this.rating = (Integer) rating; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) { this.publisher = publisher; }



}
