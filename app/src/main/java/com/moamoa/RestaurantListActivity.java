package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

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

public class RestaurantListActivity extends AppCompatActivity {

    String myJSON;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    private static final String TAG_TELEPHONE = "telephone";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_IMAGE = "image";
    JSONArray store = null;

    ArrayList<HashMap<String, String>> restaurantList;

    ImageView imageView1;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        list = (ListView) findViewById(R.id.listView);

        restaurantList = new ArrayList<HashMap<String,String>>();
        getDbData("http://ighook.cafe24.com/moamoa/GetRestaurantList.php");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(RestaurantListActivity.this, String.valueOf(restaurantList.get(position)), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RestaurantListActivity.this, StoreInfo.class);
                HashMap<String, String> data = restaurantList.get(position);
                intent.putExtra("name", data.get("name"));
                intent.putExtra("address", data.get("address"));
                intent.putExtra("telephone", data.get("telephone"));
                intent.putExtra("info", data.get("info"));
                startActivity(intent);
            }
        });
        //Toast.makeText(RestaurantListActivity.this, "open", Toast.LENGTH_SHORT).show();
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
                        //Log.d("로그", "not connected");
                    }
                    return sb.toString().trim();
                } catch(Exception e){
                    //Log.d("로그", "exception");
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
                String name = c.getString(TAG_NAME);
                String telephone = c.getString(TAG_TELEPHONE);
                String address = c.getString(TAG_ADDRESS);
                String info = c.getString("info");

                Log.d("showLost" + i, name);
                Log.d("showLost" + i, telephone);
                Log.d("showLost" + i, address);

                HashMap<String,String> restaurant = new HashMap<String,String>();
                restaurant.put(TAG_NAME, name);
                restaurant.put(TAG_TELEPHONE, telephone);
                restaurant.put(TAG_ADDRESS, address);
                restaurant.put("info", info);

                arrayList.add(new Store(name, telephone, address));

                restaurantList.add(restaurant);
            }

            MyAdapter myAdapter = new MyAdapter(this, arrayList);
            list.setAdapter(myAdapter);

            /*// 어댑터 생성, R.layout.list_item : Layout ID
            ListAdapter adapter = new SimpleAdapter(
                RestaurantListActivity.this, restaurantList, R.layout.item_list,
                new String[]{TAG_NAME,TAG_TELEPHONE,TAG_ADDRESS},
                new int[]{R.id.name, R.id.telephone, R.id.address}
            );

            list.setAdapter(adapter); // ListView 에 어댑터 설정(연결)
            //Log.d("showList", "어댑터 생성");*/

        } catch (JSONException e) {
            //Log.d("showList", "exception");
            e.printStackTrace();
        }
    }
}