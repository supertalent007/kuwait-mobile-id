package com.example.mobileid_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private OkHttpClient client;
    EditText loginUsername;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.client = new OkHttpClient();
        loginUsername = findViewById(R.id.login_pin);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginActivity.this.validateUsername()) {
                    // Check Firebase token availability
                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (task.isSuccessful()) {
                                        String token = task.getResult();
                                        if (token != null && !token.isEmpty() && LoginActivity.this.validateUsername()) {
                                            // Token is available, proceed with the request
                                            String result = task.getResult();
                                            Log.d(LoginActivity.TAG, "FCM Registration Token: " + result);
                                            LoginActivity.this.sendRegistrationToServer(result);
                                        } else {
                                            // Handle the case where the token is null or empty
                                            Log.e(TAG, "Firebase token is null or empty");
                                            // Show an error message or handle accordingly
                                        }
                                    } else {
                                        // Handle the failure of getting the token
                                        Log.e(TAG, "Failed to get Firebase token", task.getException());
                                        // Show an error message or handle accordingly
                                    }
                                }
                            });
                }
            }
        });
    }

    public void sendRegistrationToServer(String str) {
        this.client.newCall(new Request.Builder().url("http://kuwaithackers.dyndns.org:5000/registerToken").post(RequestBody.create("{\"token\":\"" + str + "\"}", MediaType.parse("application/json; charset=utf-8"))).build()).enqueue(new Callback() { // from class: com.example.sahel_app.LoginActivity.4
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Log.e(LoginActivity.TAG, "Failed to send token to server: " + iOException.getMessage());
                iOException.printStackTrace();
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(LoginActivity.TAG, "Token successfully sent to server");
                    getOTP();
                    Intent intent = new Intent(LoginActivity.this, DataActivity.class);
                    startActivity(intent);
                    return;
                }
                Log.e(LoginActivity.TAG, "Failed to send token to server: " + response.message());
            }
        });
    }

    public void getOTP() {
        this.client.newCall(new Request.Builder().url("http://kuwaithackers.dyndns.org:5000/getOTP").build()).enqueue(new Callback() { // from class: com.example.sahel_app.LoginActivity.3
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Log.e(LoginActivity.TAG, "Network request failed: " + iOException.getMessage());
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(LoginActivity.TAG, response.body().string());
                    return;
                }
                Log.e(LoginActivity.TAG, "Network request failed: " + response.message());
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Enter Your PIN");
            return false;
        }else {
            loginUsername.setError(null);
            return true;
        }
    }
}