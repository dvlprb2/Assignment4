/*
  Filename: MainActivity.java
  Student's Name: Bittu Patel
  Student ID: 200556453
  Date: December 06, 2023
*/
package com.group6.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_EDIT_REQUEST_CODE = 1;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, ADD_EDIT_REQUEST_CODE);
            }
        });

        // Fetch data from API
        fetchData();
    }

    private void fetchData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Book>> call = apiService.getBooks(token);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    // Data fetched successfully
                    List<Book> books = response.body();

                    // Render data into RecyclerView
                    renderData(books);
                } else {
                    if (response.message().equals("Unauthorized")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                Log.i("mainactivity", t.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            fetchData();
        }
    }

    private void renderData(List<Book> books) {
        BookAdapter bookAdapter = new BookAdapter(books);
        recyclerView.setAdapter(bookAdapter);

        // Set up click listener for RecyclerView items
        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                // Start AddEditActivity with intent, passing book details
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("book", book);
                startActivityForResult(intent, ADD_EDIT_REQUEST_CODE);
            }
        });
    }


}