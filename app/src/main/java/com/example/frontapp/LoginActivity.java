package com.example.frontapp;

import android.content.Intent;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontapp.UserData.User;
import com.example.frontapp.UserData.loginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private RetrofitClient retrofitClient;
    private EditText emailTextView,passwordTextView;
    private SharedPreferences loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailTextView = findViewById(R.id.emailTextView);
        passwordTextView = findViewById(R.id.passwordTextView);

        Button signUpBtn = (Button)findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginData = getSharedPreferences("UserLoginData", MODE_PRIVATE);
        boolean isLogined = loginData.getBoolean("isLogined", false);
        if(isLogined)
        {
            String email = loginData.getString("email", null);
            String password = loginData.getString("password",null);
            emailTextView.setText(email);
            passwordTextView.setText(password);
        }

        Button loginBtn  = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new  Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                loginRequest loginrequest = new loginRequest(email,password);
                System.out.println(loginrequest.getEmail());
                //로그인 수행 POST
                retrofitClient = RetrofitClient.getInstance();
                ServiceApi serviceApi = RetrofitClient.getRetrofitInterface();
                serviceApi.loginServer(loginrequest).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            System.out.println("POST Success");
                            User data = response.body();
                            putData(data,password);
                            Log.d("TEST", "POST 성공");
                            Log.d("TEST", data.getToken());
                            //사진 선택하는 화면으로 넘어감
                            Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Log.d("TEST", "POST Failed");
                            Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호가 잘못되었습니다. 확인 후 다시 시도해 주세요.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("FAIL", t.getMessage());
                    }
                });

            }
        });
    }

    private void putData(User data, String password)
    {
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("email",data.getEmail());
        editor.putString("password",password);
        editor.putString("token",data.getToken());
        editor.putString("nickname",data.getNickname());
        editor.commit();
    }
}