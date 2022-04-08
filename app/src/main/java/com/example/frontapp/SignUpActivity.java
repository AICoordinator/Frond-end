package com.example.frontapp;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontapp.UserData.User;
import com.example.frontapp.RetrofitClient;
import com.example.frontapp.UserData.signUpRequest;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    EditText emailTextView, passwordTextView, nickNameTextView;
    RadioGroup genderGroup;
    RetrofitClient retrofitClient;
    private SharedPreferences loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //view 아이디 연결
        signUp = findViewById(R.id.doSignUpBtn);
        emailTextView = findViewById(R.id.emailTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        nickNameTextView = findViewById(R.id.nickNameTextView);
        genderGroup = findViewById(R.id.genderGroup);

        //회원가입 버튼 클릭 리스너
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String nickname = nickNameTextView.getText().toString();
                int gender;
                int id = genderGroup.getCheckedRadioButtonId();
                if(id == R.id.manBtn)
                    gender = 1;
                else
                    gender = 0;

                HashMap<String, Object> input = new HashMap<>();
                input.put("email", email);
                input.put("gender", gender);
                input.put("nickname", nickname);
                input.put("password", password);

                signUpRequest signUpRequest = new signUpRequest(email,password,gender,nickname);
                //POST
                retrofitClient = RetrofitClient.getInstance();
                ServiceApi serviceApi = RetrofitClient.getRetrofitInterface();
                //User data = new User(email, password, gender, nickname);
                serviceApi.postData(signUpRequest).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            System.out.println("POST Success");
                            User data = response.body();
                            putData(data,password);
                            Log.d("TEST", "POST 성공");
                            Log.d("TEST", data.getEmail());
                        }
                        else {
                            Log.d("TEST", "POST Failed");
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
        loginData = getSharedPreferences("UserLoginData", MODE_PRIVATE);;
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("email",data.getEmail());
        editor.putString("password",password);
        editor.putBoolean("isLogined",true);
        editor.putString("token",data.getToken());
        editor.putString("nickname",data.getNickname());
        editor.commit();
    }

}