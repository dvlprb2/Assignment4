/*
  Filename: ApiService.java
  Student's Name: Bittu Patel
  Student ID: 200556453
  Date: December 06, 2023
*/

package com.group6.assignment4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/register")
    Call<User> registerUser(@Body User userRequest);

    @POST("/login")
    Call<User> loginUser(@Body User user);

    // Retrieve all books
    @GET("/books")
    Call<List<Book>> getBooks(@Header("x-access-token") String token);

    // Create a new book
    @POST("/books")
    Call<Book> createBook(@Header("x-access-token") String token, @Body Book book);

    // Update an existing book
    @PUT("/books/{id}")
    Call<Book> updateBook(@Header("x-access-token") String token, @Path("id") String bookId, @Body Book book);

    // Delete a book
    @DELETE("/books/{id}")
    Call<Void> deleteBook(@Header("x-access-token") String token, @Path("id") String bookId);

}
