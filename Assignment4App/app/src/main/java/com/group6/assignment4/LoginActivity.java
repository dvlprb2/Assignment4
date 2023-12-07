/*
  Filename: LoginActivity.java
  Student's Name: Sinthuvamsan Viswarupan
  Student ID: 200557495
  Date: December 06, 2023
*/
package com.group6.assignment4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;

    // Create ApiService instance
    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        TextView textRegister = findViewById(R.id.textRegister);
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Retrieve the SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Check if a token is already saved (e.g., if the user is already logged in)
        String savedToken = sharedPreferences.getString("token", "");

        if (!TextUtils.isEmpty(savedToken)) {
            // If a token is saved, directly go to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("token", savedToken);
            startActivity(intent);
            finish();
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            // Handle empty fields
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create User object
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        // Make API call
        Call<User> call = apiService.loginUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    // API call successful, handle response
                    User userResponse = response.body();

                    assert userResponse != null;
                    String token = userResponse.getToken();

                    // Save the token to SharedPreferences
                    saveTokenToLocalStorage(token);

                    // Perform intent to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                } else {
                    // API call failed, handle error
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                // API call failed, handle error
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTokenToLocalStorage(String token) {
        // Save the token to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
