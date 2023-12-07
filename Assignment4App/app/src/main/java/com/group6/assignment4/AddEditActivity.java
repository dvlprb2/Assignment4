/*
  Filename: AddEditActivity.java
  Student's Name: Bittu Patel
  Student ID: 200556453
  Date: December 06, 2023
*/
package com.group6.assignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditActivity extends AppCompatActivity {

    private EditText nameText, isbnText, ratingText, authorText, publisherText;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        nameText = findViewById(R.id.nameText);
        isbnText = findViewById(R.id.isbnText);
        ratingText = findViewById(R.id.ratingText);
        authorText = findViewById(R.id.authorText);
        publisherText = findViewById(R.id.publisherText);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button updateButton = findViewById(R.id.updateButton);

        if (getIntent().hasExtra("book")) {
            isEditMode = true;
            Book book = (Book) getIntent().getSerializableExtra("book");
            assert book != null;
            updateUIForEdit(book);
        }

        TextView addEditTextView = findViewById(R.id.AddEditText);
        addEditTextView.setText(isEditMode ? "Edit Book" : "Add Book");

        updateButton.setText(isEditMode ? "Update" : "Save");
        deleteButton.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditMode) {
                    updateBook();
                } else {
                    createBook();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
            }
        });
    }

    private void updateUIForEdit(Book book) {
        nameText.setText(book.getName());
        isbnText.setText(book.getISBN());
        ratingText.setText(String.valueOf(book.getRating()));
        authorText.setText(book.getAuthor());
        publisherText.setText(book.getPublisher());
    }

    private void createBook() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        String name = nameText.getText().toString();
        String isbn = isbnText.getText().toString();
        String rating = ratingText.getText().toString();
        String author = authorText.getText().toString();
        String publisher = publisherText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(isbn) || TextUtils.isEmpty(rating)
                || TextUtils.isEmpty(author) || TextUtils.isEmpty(publisher)) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        Book newBook = new Book();
        newBook.setName(name);
        newBook.setISBN(isbn);
        newBook.setRating(Integer.parseInt(rating));
        newBook.setAuthor(author);
        newBook.setPublisher(publisher);

        Call<Book> call = apiService.createBook(getToken(), newBook);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddEditActivity.this, "Book created successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddEditActivity.this, "Failed to create book", Toast.LENGTH_SHORT).show();
                    Log.i("response addedit", response.message());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(AddEditActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBook() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        String name = nameText.getText().toString();
        String isbn = isbnText.getText().toString();
        String rating = ratingText.getText().toString();
        String author = authorText.getText().toString();
        String publisher = publisherText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(isbn) || TextUtils.isEmpty(rating)
                || TextUtils.isEmpty(author) || TextUtils.isEmpty(publisher)) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        Book updatedBook = new Book();
        updatedBook.setName(name);
        updatedBook.setISBN(isbn);
        updatedBook.setRating(Integer.parseInt(rating));
        updatedBook.setAuthor(author);
        updatedBook.setPublisher(publisher);

        Book originalBook = (Book) getIntent().getSerializableExtra("book");
        assert originalBook != null;
        String bookId = originalBook.getID();
        Log.i("bookID", bookId);

        Call<Book> call = apiService.updateBook(getToken(), bookId, updatedBook);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddEditActivity.this, "Book updated successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddEditActivity.this, "Failed to update book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(AddEditActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBook() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Book bookToDelete = (Book) getIntent().getSerializableExtra("book");
        assert bookToDelete != null;
        String bookId = bookToDelete.getID();
        Log.i("bookID", bookId);

        Call<Void> call = apiService.deleteBook(getToken(), bookId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddEditActivity.this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddEditActivity.this, "Failed to delete book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddEditActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}