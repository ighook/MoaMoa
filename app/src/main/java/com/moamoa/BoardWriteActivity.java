package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardWriteActivity extends AppCompatActivity {

    EditText et_title, et_content;
    Button btn_write;
    String nicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        nicName = getIntent().getStringExtra("nicName");

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

        btn_write = findViewById(R.id.btn_writing);
        btn_write.setOnClickListener(write_click);
    }

    protected View.OnClickListener write_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegisterBoard();
        }
    };


    public void RegisterBoard() {
        String url = "http://ighook.cafe24.com/moamoa/GetIndex.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    int index = jsonObject.getInt("index");
                    Upload(index);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void Upload(int num) {
        String index = String.valueOf(num);
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();
        String writer = nicName;
        String date = getDate();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success) {
                        Toast.makeText(getApplicationContext(), "게시글을 성공적으로 저장하였습니다", Toast.LENGTH_SHORT).show();

                        BoardListActivity boardListActivity = (BoardListActivity) BoardListActivity.activity;
                        boardListActivity.finish();

                        Intent intent = new Intent(BoardWriteActivity.this, BoardListActivity.class);
                        startActivity(intent);

                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "게시글을 작성하는데 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        };
        BoardWriteRequest request = new BoardWriteRequest(index, title, content, writer, date, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    // 날짜 가져오기
    public String getDate() {
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        // nowDate 변수에 값을 저장한다.
        return sdfNow.format(date);
    }
}