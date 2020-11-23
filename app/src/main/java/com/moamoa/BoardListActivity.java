package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.misc.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BoardListActivity extends AppCompatActivity {

    public static Activity activity;

    String myJSON;
    JSONArray board = null;
    ImageButton btn_board_write;
    ArrayList<HashMap<String, String>> boardList;
    ListView list;
    String nicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        activity = this;

        boardList = new ArrayList<HashMap<String,String>>();
        list = findViewById(R.id.boardListView);
        getDbData("http://ighook.cafe24.com/moamoa/GetBoardList.php");

        btn_board_write = findViewById(R.id.btn_board_write);

        GetUserInfo();

        // 버튼 클릭 리스너
        btn_board_write.setOnClickListener(board_write_click);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> data = boardList.get(position);
                Intent intent = new Intent(BoardListActivity.this, BoardActivity.class);
                intent.putExtra("title", data.get("title"));
                intent.putExtra("content", data.get("content"));
                intent.putExtra("date", data.get("date"));
                startActivity(intent);
            }
        });
    }

    private void getDbData(String url) {
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

                    if(conn != null){ // 연결되었으면
                        conn.setConnectTimeout(10000);
                        conn.setUseCaches(false);
                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){ // 연결 코드가 리턴되면
                            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String json;
                            while((json = bufferedReader.readLine())!= null){
                                sb.append(json + "\n");
                            }
                        }
                        Log.d("Notice", "connected");
                    }
                    else {
                        Log.d("Notice", "not connected");
                    }
                    return sb.toString().trim();
                } catch(Exception e){
                    Log.d("Notice", "exception");
                    return new String("Exception: " + e.getMessage());
                }
            }

            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }

        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            board = jsonObj.getJSONArray("result");
            Log.d("Notice", "show try");

            for(int i = 0; i < board.length(); i++){
                JSONObject c = board.getJSONObject(i);

                String index = c.getString("index");
                String title = c.getString("title");
                String writer = c.getString("writer");
                String content = c.getString("content");
                String date = c.getString("date");

                Log.d("Notice", "index : " + index);
                Log.d("Notice", "title : " + title);
                Log.d("Notice", "content : " + content);
                Log.d("Notice", "date : " + date);

                HashMap<String,String> notice = new HashMap<String,String>();

                notice.put("index", index);
                notice.put("writer", writer);
                notice.put("title", title);
                notice.put("content", content);
                notice.put("date", date);

                boardList.add(notice);
            }

            // 어댑터 생성, R.layout.list_item : Layout ID
            ListAdapter adapter = new SimpleAdapter(
                    BoardListActivity.this, boardList, R.layout.board_item_list,
                    new String[]{"title", "writer", "date"},
                    new int[]{R.id.title, R.id.writer, R.id.date}
            );

            list.setAdapter(adapter); // ListView 에 어댑터 설정(연결)
            Log.d("Notice", "어댑터 생성");

        } catch (JSONException e) {
            Log.d("Notice", "exception");
            e.printStackTrace();
        }
    }

    private View.OnClickListener board_write_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!nicName.isEmpty()) {
                Intent intent = new Intent(BoardListActivity.this, BoardWriteActivity.class);
                intent.putExtra("nicName", nicName);
                startActivity(intent);
            } else {
                Intent intent = new Intent(BoardListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };

    private void GetUserInfo() {
        SharedPreferences sp = this.getSharedPreferences("Login",0);
        nicName = sp.getString("nicName", nicName);
    }
}