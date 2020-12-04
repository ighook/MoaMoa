package com.moamoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.misc.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //zz
    ImageButton btn_login, btn_search, btn_restaurant, btn_cafe;
    TextView notice_detail_text, notice1_title, notice1_content, notice1_date, notice2_title, notice2_content, notice2_date;
    TextView board_detail_text, board1_title, board1_content ,board1_date, board2_title, board2_content ,board2_date;

    String myJSON;
    static String ID = null;
    static String PW = null;
    static String nicName = null;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        GetUserInfo();

        notice_detail_text = findViewById(R.id.notice_detail);
        board_detail_text = findViewById(R.id.board_detail);
        btn_login = findViewById(R.id.btn_login);
        btn_search = findViewById(R.id.btn_search);
        btn_restaurant = findViewById(R.id.btn_restaurant);
        btn_cafe = findViewById(R.id.btn_cafe);

        notice1_title = findViewById(R.id.notice1_title);
        notice2_title = findViewById(R.id.notice2_title);
        notice1_date = findViewById(R.id.notice1_date);
        notice2_date = findViewById(R.id.notice2_date);
        notice1_content = findViewById(R.id.notice1_content);
        notice2_content = findViewById(R.id.notice2_content);

        board1_title = findViewById(R.id.board1_title);
        board2_title = findViewById(R.id.board2_title);
        board1_date = findViewById(R.id.board1_date);
        board2_date = findViewById(R.id.board2_date);
        board1_content = findViewById(R.id.board1_content);
        board2_content = findViewById(R.id.board2_content);

        GetNotice();
        GetBoard();

        // 로그인 버튼
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ID == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("로그아웃하시겠습니까?");
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Logout();
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }
            }
        });

        // 카페 버튼
        btn_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, ReviewListActivity.class);
                Intent intent = new Intent(MainActivity.this, CafeListActivity.class);
                intent.putExtra("nicName", nicName);
                startActivity(intent);
            }
        });

        // 식당 버튼
        btn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, ReviewListActivity.class);
                Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
                intent.putExtra("nicName", nicName);
                startActivity(intent);
            }
        });

        // 공지사항 더보기
        notice_detail_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoticeListActivity.class);
                startActivity(intent);
            }
        });

        // 자유게시판 더보기
        board_detail_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoardListActivity.class);
                startActivity(intent);
            }
        });

        // 공지사항 클릭
        notice1_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = notice1_title.getText().toString();
                String content = notice1_content.getText().toString();
                String date = notice1_date.getText().toString();
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        notice2_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = notice2_title.getText().toString();
                String content = notice2_content.getText().toString();
                String date = notice2_date.getText().toString();
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
        aDialog.setMessage("종료하시겠습니까?");
        aDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        aDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = aDialog.create();
        alertDialog.show();
    }

    private void GetUserInfo() {
        SharedPreferences sp = this.getSharedPreferences("Login",0);
        ID = sp.getString("ID", null);
        PW = sp.getString("PW", null);
        nicName = sp.getString("nicName", null);
    }

    public static boolean LoginCheck() {
        return ID != null;
    }

    private void Logout() {
        SharedPreferences sp = this.getSharedPreferences("Login",0);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.clear();
        Ed.apply();
        ID = null;
        PW = null;
        nicName = null;
    }

    public void GetNotice() {
        String url = "http://ighook.cafe24.com/moamoa/GetNotice_Main.php";
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    Log.d("Notice", "try");
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 연결 객체 생성
                    StringBuilder sb = new StringBuilder();

                    if (conn != null) { // 연결되었으면
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 연결 코드가 리턴되면
                            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String json;
                            while ((json = bufferedReader.readLine()) != null) {
                                sb.append(json + "\n");
                            }
                        }
                        Log.d("Notice", "connected");
                    } else {
                        Log.d("Notice", "not connected");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d("Notice", "exception");
                    return new String("Exception: " + e.getMessage());
                }
            }

            protected void onPostExecute(String result) {
                myJSON = result;
                showNotice();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public void GetBoard() {
        String url = "http://ighook.cafe24.com/moamoa/GetBoardtoMain.php";
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    Log.d("Notice", "try");
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 연결 객체 생성
                    StringBuilder sb = new StringBuilder();

                    if (conn != null) { // 연결되었으면
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 연결 코드가 리턴되면
                            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String json;
                            while ((json = bufferedReader.readLine()) != null) {
                                sb.append(json + "\n");
                            }
                        }
                    } else {
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }

            protected void onPostExecute(String result) {
                myJSON = result;
                showBoard();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    protected void showNotice(){
        JSONArray notice;
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            notice = jsonObj.getJSONArray("result");

            JSONObject notice1 = notice.getJSONObject(0);
            JSONObject notice2 = notice.getJSONObject(1);
            String notice1_title_text = notice1.getString("title");
            String notice1_date_text = notice1.getString("date");
            String notice1_content_text = notice1.getString("content");
            String notice2_title_text = notice2.getString("title");
            String notice2_date_text = notice1.getString("date");
            String notice2_content_text = notice2.getString("content");

            notice1_title.setText(notice1_title_text);
            notice1_content.setText(notice1_content_text);
            notice1_date.setText(notice1_date_text);
            notice2_title.setText(notice2_title_text);
            notice2_content.setText(notice2_content_text);
            notice2_date.setText(notice2_date_text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void showBoard(){
        JSONArray board;
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            board = jsonObj.getJSONArray("result");

            JSONObject board1 = board.getJSONObject(0);
            JSONObject board2 = board.getJSONObject(1);
            String board1_title_text = board1.getString("title");
            String board1_date_text = board1.getString("date");
            String board1_content_text = board1.getString("content");
            String board2_title_text = board2.getString("title");
            String board2_date_text = board1.getString("date");
            String board2_content_text = board2.getString("content");

            board1_title.setText(board1_title_text);
            board1_content.setText(board1_content_text);
            board1_date.setText(board1_date_text);
            board2_title.setText(board2_title_text);
            board2_content.setText(board2_content_text);
            board2_date.setText(board2_date_text);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}