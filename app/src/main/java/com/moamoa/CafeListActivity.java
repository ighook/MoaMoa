package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.misc.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CafeListActivity extends AppCompatActivity {

    Bitmap bitmap;
    String myJSON;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    private static final String TAG_TELEPHONE = "telephone";
    private static final String TAG_ADDRESS = "address";
    JSONArray store = null;

    ArrayList<HashMap<String, String>> cafeList;

    ImageView imageView1;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_list);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        list = (ListView) findViewById(R.id.listView);
        cafeList = new ArrayList<HashMap<String,String>>();
        getDbData("http://ighook.cafe24.com/moamoa/GetCafeList.php");

        // 리뷰 선택
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(RestaurantListActivity.this, String.valueOf(restaurantList.get(position)), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CafeListActivity.this, RestaurantInfo.class);
                HashMap<String, String> data = cafeList.get(position);
                intent.putExtra("name", data.get("name"));
                intent.putExtra("address", data.get("address"));
                intent.putExtra("telephone", data.get("telephone"));
                startActivity(intent);
                finish();
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
                    Log.d("로그", "try");
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
                        Log.d("로그", "connected");
                    }
                    else {
                        Log.d("로그", "not connected");
                    }
                    return sb.toString().trim();
                } catch(Exception e){
                    Log.d("로그", "exception");
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
        ArrayList<Store> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            store = jsonObj.getJSONArray(TAG_RESULTS);
            Log.d("showList", "try");
            for(int i=0; i<store.length(); i++){
                JSONObject c = store.getJSONObject(i);
                final String name = c.getString(TAG_NAME);
                String telephone = c.getString(TAG_TELEPHONE);
                String address = c.getString(TAG_ADDRESS);

                Log.d("showLost" + i, name);
                Log.d("showLost" + i, telephone);
                Log.d("showLost" + i, address);

                HashMap<String,String> cafe = new HashMap<String,String>();

                cafe.put(TAG_NAME, name);
                cafe.put(TAG_TELEPHONE, telephone);
                cafe.put(TAG_ADDRESS, address);

                arrayList.add(new Store(name, telephone, address));

                cafeList.add(cafe);
            }

            MyAdapter myAdapter = new MyAdapter(this, arrayList);
            list.setAdapter(myAdapter);
            /*URL url = new URL("http://ighook.cafe24.com/ighook/www/moamoa/cafe_image/villain.jpg");
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");*/
            // 어댑터 생성, R.layout.list_item : Layout ID
            /*ListAdapter adapter = new SimpleAdapter(
                CafeListActivity.this, cafeList, R.layout.item_list,
                new String[]{TAG_NAME,TAG_TELEPHONE,TAG_ADDRESS},
                new int[]{R.id.name, R.id.telephone, R.id.address}
            );

            list.setAdapter(adapter); // ListView 에 어댑터 설정(연결)
            Log.d("showList", "어댑터 생성");*/

        } catch (JSONException e) {
            Log.d("showList", "exception");
            e.printStackTrace();
        }
    }
}