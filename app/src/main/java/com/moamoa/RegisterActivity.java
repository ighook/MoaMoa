package com.moamoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    EditText et_id, et_pw, et_pw_check, et_name, et_nicName;
    TextView alert_id, alert_pw, alert_pw_check, alert_nicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_pw_check = findViewById(R.id.et_pw_check);
        et_name = findViewById(R.id.et_name);
        et_nicName = findViewById(R.id.et_nicName);

        alert_id = findViewById(R.id.alert_id);
        alert_pw = findViewById(R.id.alert_pw);
        alert_pw_check = findViewById(R.id.alert_pw_check);
        alert_nicName = findViewById(R.id.alert_nicName);

        textChanged();
        passwordEqualCheck();

        // 가입 버튼
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText 내용 가져오기
                String userID = et_id.getText().toString();
                String userPassword = et_pw.getText().toString();
                String userPasswordCheck = et_pw_check.getText().toString();
                String userName = et_name.getText().toString();
                String userNicName = et_nicName.getText().toString();

                if(userID.length() < 6 || userID.length() > 15) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("아이디는 6~15 글자로 사용 가능합니다")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                    return;
                }

                if(userPassword.length() == 0 || userPasswordCheck.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("비밀번호를 입력해주세요")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                    return;
                }

                if(!userPassword.equals(userPasswordCheck)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("비밀번호가 일치하지 않습니다")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                    return;
                }

                if(userNicName.length() < 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("닉네임은 두 글자부터 사용 가능합니다")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int state = jsonObject.getInt("state");

                            if (state == 1) { // 회원 등록 성공
                                Toast.makeText(getApplicationContext(), "가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (state == 2) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("이미 존재하는 아이디입니다")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else if (state == 3) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("아이디가 너무 길거나 짧습니다")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userNicName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    public void textChanged() {
        et_id.addTextChangedListener(new TextWatcher() {
            @SuppressLint("SetTextI18n")
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    alert_id.setText("아이디를 입력해주세요");
                    alert_id.setTextSize(12);
                    alert_id.setTextColor(Color.RED);
                }
                else if(s.length() < 6 || s.length() > 15){
                    alert_id.setText("6~15자로 입력해주세요");
                    alert_id.setTextSize(12);
                    alert_id.setTextColor(Color.RED);
                }
                else {
                    alert_id.setText("");
                    alert_id.setTextSize(0);
                    alert_id.setTextColor(Color.BLACK);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        et_pw.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = s.toString();
                String pw_check = et_pw_check.getText().toString();
                if(!pw.equals(pw_check)) {
                    alert_pw_check.setText("비밀번호가 일치하지 않습니다");
                    alert_pw_check.setTextSize(12);
                    alert_pw_check.setTextColor(Color.RED);
                }
                else {
                    alert_pw_check.setText("");
                    alert_pw_check.setTextSize(0);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        et_pw_check.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = et_pw.getText().toString();
                String pw_check = s.toString();
                if(!pw_check.equals(pw)) {
                    alert_pw_check.setText("비밀번호가 일치하지 않습니다");
                    alert_pw_check.setTextSize(12);
                    alert_pw_check.setTextColor(Color.RED);
                }
                else {
                    alert_pw_check.setText("");
                    alert_pw_check.setTextSize(0);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        et_nicName.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nicName = s.toString();
                if(nicName.length() < 2) {
                    alert_nicName.setText("닉네임은 두 글자부터 사용가능합니다");
                    alert_nicName.setTextSize(12);
                    alert_nicName.setTextColor(Color.RED);
                }
                else {
                    alert_nicName.setText("");
                    alert_nicName.setTextSize(0);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void passwordEqualCheck() {
        if(!et_pw.equals(et_pw_check)) {
            alert_pw_check.setText("비밀번호가 일치하지 않습니다");
            alert_pw_check.setTextSize(12);
            alert_pw_check.setTextColor(Color.RED);
        }
        else {
            alert_pw_check.setText("");
            alert_pw_check.setTextSize(0);
        }
    }
}