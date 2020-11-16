package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.misc.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class NoticeListActivity extends AppCompatActivity {

    String myJSON;
    private static final String TAG_RESULTS="result";
    private static final String TAG_INDEX = "index";
    private static final String TAG_TITLE = "title";
    private static final String TAG_WRITER = "writer";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATE = "date";
    JSONArray notice = null;
    ArrayList data = null;
    ArrayList<HashMap<String, String>> noticeList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        noticeList = new ArrayList<HashMap<String,String>>();
        list = findViewById(R.id.noticeListView);
        Log.d("Notice", "activity opened");
        getDbData("http://ighook.cafe24.com/moamoa/NoticeRead.php");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> data = noticeList.get(position);
                Intent intent = new Intent(NoticeListActivity.this, NoticeActivity.class);
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
            notice = jsonObj.getJSONArray(TAG_RESULTS);
            Log.d("Notice", "show try");

            for(int i = 0; i < notice.length(); i++){
                JSONObject c = notice.getJSONObject(i);

                String index = c.getString(TAG_INDEX);
                String title = c.getString(TAG_TITLE);
                String writer = "운영자";
                String content = c.getString(TAG_CONTENT);
                String date = c.getString(TAG_DATE);

                Log.d("Notice", "index : " + index);
                Log.d("Notice", "title : " + title);
                Log.d("Notice", "content : " + content);
                Log.d("Notice", "date : " + date);

                HashMap<String,String> notice = new HashMap<String,String>();

                notice.put(TAG_INDEX, index);
                notice.put(TAG_WRITER, writer);
                notice.put(TAG_TITLE, title);
                notice.put(TAG_CONTENT, content);
                notice.put(TAG_DATE, date);

                noticeList.add(notice);
            }

            // 어댑터 생성, R.layout.list_item : Layout ID
            ListAdapter adapter = new SimpleAdapter(
                    NoticeListActivity.this, noticeList, R.layout.notice_item_list,
                    new String[]{TAG_TITLE, TAG_WRITER, TAG_DATE},
                    new int[]{R.id.title, R.id.writer, R.id.date}
            );

            list.setAdapter(adapter); // ListView 에 어댑터 설정(연결)
            Log.d("Notice", "어댑터 생성");

        } catch (JSONException e) {
            Log.d("Notice", "exception");
            e.printStackTrace();
        }
    }
}