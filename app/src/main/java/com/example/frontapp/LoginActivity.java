package com.example.frontapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontapp.UserData.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private RetrofitClient retrofitClient;
    private EditText emailTextView,passwordTextView;

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

        Button loginBtn  = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new  Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                User data = new User(email, password,1,"aaa");

                //로그인 수행 POST
                retrofitClient = RetrofitClient.getInstance();
                ServiceApi serviceApi = RetrofitClient.getRetrofitInterface();
                serviceApi.loginServer(data).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            System.out.println("POST Success");
                            User data = response.body();
                            //System.out.println(response.body());
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

}