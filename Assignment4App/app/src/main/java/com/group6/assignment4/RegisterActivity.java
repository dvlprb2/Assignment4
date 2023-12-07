/*
  Filename: RegisterActivity.java
  Student's Name: Sinthuvamsan Viswarupan
  Student ID: 200557495
  Date: December 06, 2023
*/
package com.group6.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonSignup = findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        TextView textLogin = findViewById(R.id.textLogin);
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        Log.i("RegisterActivity", "calling register user");
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            // Handle empty fields
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create ApiService instance
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Create User object
        User userRequest = new User();
        userRequest.setName(name);
        userRequest.setEmail(email);
        userRequest.setPassword(password);

        // Make API call
        Call<User> call = apiService.registerUser(userRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // API call successful, handle response
                    User user = response.body();

                    assert user != null;
                    Log.i("RegisterActivity", user.toString());

                    String token = user.getToken();
                    Log.i("RegisterActivity", token.toString());


                    // Perform intent to login activity
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                } else {
                    // API call failed, handle error
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // API call failed, handle error
                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                Log.i("RegisterActivity", t.toString());
            }
        });
    }
}
