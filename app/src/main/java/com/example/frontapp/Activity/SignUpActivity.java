package com.example.frontapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.frontapp.Communication.ServiceApi;
import com.example.frontapp.R;
import com.example.frontapp.UserData.User;
import com.example.frontapp.Communication.RetrofitClient;
import com.example.frontapp.UserData.UserDataRepository;
import com.example.frontapp.UserData.signUpRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    EditText emailTextView, passwordTextView, nickNameTextView;
    RadioGroup genderGroup;
    RetrofitClient retrofitClient;
    private SharedPreferences loginData;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = this;

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
                signUpRequest signUpRequest = new signUpRequest(email,nickname,gender,password);
                //POST
                retrofitClient = RetrofitClient.getInstance();
                ServiceApi serviceApi = RetrofitClient.getRetrofitInterface();
                serviceApi.postData(signUpRequest).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            User data = response.body();
                            if(data != null)
                                UserDataRepository.setAllUserData(mContext,data,password);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
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

}