package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RegisterPersonalActivity extends AppCompatActivity {
    EditText input_id, input_pw, input_pw_check, input_name, input_age;
    TextView error_id, error_pw, error_name, error_age, error_pw_check;
    Button btn_register;
    String id, pw, pwCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_personal);

        input_id = (EditText) findViewById(R.id.id);
        input_pw = (EditText) findViewById(R.id.password);
        input_pw_check = (EditText) findViewById(R.id.password_check);
        input_name = (EditText) findViewById(R.id.name);
        input_age = (EditText) findViewById(R.id.age);

        error_id = findViewById(R.id.id_error);
        error_pw = findViewById(R.id.pw_error);
        error_name = findViewById(R.id.name_error);
        error_age = findViewById(R.id.age_error);
        error_pw_check = findViewById(R.id.pw_check_error);

        // 가입 버튼
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 에러 표시 초기화
                ResetEditText();

                // EditText 내용 가져오기
                String userID = input_id.getText().toString();
                String userPassword = input_pw.getText().toString();
                String userPasswordCheck = input_pw_check.getText().toString();
                String userName = input_name.getText().toString();
                String age = input_age.getText().toString();
                int userAge = 0;
                if(!age.isEmpty()) userAge = Integer.parseInt(age);

                //Toast.makeText(getApplicationContext(), userID + " " + userPassword, Toast.LENGTH_SHORT).show();
                /*if(!EmptyCheck(userID, userPassword, userPasswordCheck, userName, age)) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int state = jsonObject.getInt("state");

                                if(state == 1) { // 회원 등록 성공
                                    Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterPersonalActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(state == 2) {
                                    //Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                                    error_id.setText("이미 존재하는 아이디입니다");
                                }
                                else { // 회원 등록 실패
                                    Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userNicName, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterPersonalActivity.this);
                    queue.add(registerRequest);
                }*/

                /*if(!userPassword.equals(userPasswordCheck)) {
                    //Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    error_pw.setText("비밀번호가 일치하지 않습니다");
                    return;
                }*/
                /*
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int state = jsonObject.getInt("state");

                            if(state == 1) { // 회원 등록 성공
                                Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterPersonalActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(state == 2) {
                                //Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                                error_id.setText("이미 존재하는 아이디입니다");
                            }
                            else { // 회원 등록 실패
                                Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterPersonalActivity.this);
                queue.add(registerRequest);*/
            }
        });
    }

    public void personal (View v) {
        Intent intent = new Intent(getBaseContext(), RegisterBusinessActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean EmptyCheck(String... args) {
        //Toast.makeText(getApplicationContext(), "항목이 비어있습니다", Toast.LENGTH_SHORT).show();
        boolean error = false;
        if(args[0].isEmpty()) {
            error_id.setText("아이디를 입력해주세요");
            error = true;
        }
        if(args[1].isEmpty()) {
            error_pw.setText("비밀번호를 입력해주세요");
            error = true;
        }
        if(args[2].isEmpty()) {
            error_pw_check.setText("비밀번호를 재입력해주세요");
            error = true;
        }
        if(args[3].isEmpty()) {
            error_name.setText("이름을 입력해주세요");
            error = true;
        }
        if(args[4].isEmpty()) {
            error_age.setText("나이를 입력해주세요");
            error = true;
        }
        return error;
    }

    public void ResetEditText() {
        error_id.setText("");
        error_pw.setText("");
        error_pw_check.setText("");
        error_name.setText("");
        error_age.setText("");
    }
/*
    public void register(View v) {
        id = input_id.getText().toString();
        pw = input_pw.getText().toString();
        pwCheck = input_pw_check.getText().toString();

        //Toast.makeText(this, "아이디 : " + id + "\n비밀번호 : " + pw + "\n비밀번호 확인 : " + pwCheck, Toast.LENGTH_SHORT).show();

        if(id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || ) {
            Toast.makeText(this, "항목이 비어있습니다", Toast.LENGTH_SHORT).show();
        }
        else if(!pw.equals(pwCheck)){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(this, "아이디 : " + id + "\n비밀번호 : " + pw, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "가입이 완료되었습니다", Toast.LENGTH_SHORT).show();

            String errorString;
            String serverURL = "http://ighook.cafe24.com/moamoa/register.php";
            String postParameter = "?id=" + id + "&pw=" + pw;

        }
    }*/
}