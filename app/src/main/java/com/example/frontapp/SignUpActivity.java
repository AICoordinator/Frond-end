package com.example.frontapp;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    Button signUp;
    EditText emailTextView, passwordTextView, nickNameTextView;
    RadioGroup genderGroup;
    ServiceApi serviceApi;
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
                boolean gender;
                int id = genderGroup.getCheckedRadioButtonId();
                if(id == R.id.manBtn)
                    gender = true;
                else
                    gender = false;
                startJoin(new User(email, password, gender, nickname));
            }
        });
    }

    private void startJoin(User data) {
        Retrofit retrofit = RetrofitClient.getClient();
        serviceApi = retrofit.create(ServiceApi.class);
        serviceApi.userJoin(data).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse result = response.body();
                Toast.makeText(SignUpActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getStatus() == 200)
                    finish();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                Log.e("Sign up Error", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}