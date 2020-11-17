package com.moamoa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.misc.AsyncTask;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantInfo extends AppCompatActivity {

    TextView name_text, map_info_bar, menu_bar, review_bar;
    Button btn_review_write;
    LinearLayout btn_map_info, btn_menu, btn_review;
    ListView review_list;
    RelativeLayout review_screen;
    String name;
    String myJSON;
    JSONArray review;
    ArrayList<HashMap<String, String>> reviewList = new ArrayList<>();

    private static final String TAG_RESULTS="result";
    private static final String TAG_CONTENT="content";
    private static final String TAG_WRITER="writer";
    private static final String TAG_DATE="date";
    private static final String TAG_EVAL1="eval1";
    private static final String TAG_EVAL2="eval2";
    private static final String TAG_EVAL3="eval3";
    private static final String TAG_EVAL4="eval4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        // list id
        review_list = findViewById(R.id.review_list);

        // screen id
        review_screen = findViewById(R.id.review_screen);

        // 버튼 id
        btn_map_info = findViewById(R.id.btn_map_info);
        btn_menu = findViewById(R.id.btn_menu);
        btn_review = findViewById(R.id.btn_review);
        btn_review_write = findViewById(R.id.btn_review_write);

        // Bar id
        map_info_bar = findViewById(R.id.map_info_bar);
        menu_bar = findViewById(R.id.menu_bar);
        review_bar = findViewById(R.id.review_bar);

        // 상단 상호명 변경
        name = getIntent().getStringExtra("name");
        name_text = findViewById(R.id.name);
        name_text.setText(name);

        // 클릭 리스너
        btn_map_info.setOnClickListener(map_info_click);
        btn_menu.setOnClickListener(menu_click);
        btn_review.setOnClickListener(review_click);
        btn_review_write.setOnClickListener(review_write_click);

        // 리뷰 레이아웃 비활성화
        review_screen.setVisibility(View.INVISIBLE);

        // 리뷰 불러옴
        GetReview();

        // 리뷰 선택
        review_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> data = reviewList.get(position);
                Intent intent = new Intent(RestaurantInfo.this, ReviewShow.class);
                intent.putExtra("writer", data.get("writer"));
                intent.putExtra("content", data.get("content"));
                intent.putExtra("date", data.get("date"));
                intent.putExtra("eval1", data.get("eval1"));
                intent.putExtra("eval2", data.get("eval2"));
                intent.putExtra("eval3", data.get("eval3"));
                intent.putExtra("eval4", data.get("eval4"));
                intent.putExtra("image1", data.get("image1"));
                intent.putExtra("image2", data.get("image2"));
                intent.putExtra("image3", data.get("image3"));
                intent.putExtra("image4", data.get("image4"));
                Log.d("image1", String.valueOf(data.get("image1")));
                Log.d("image2", String.valueOf(data.get("image2")));
                Log.d("image3", String.valueOf(data.get("image3")));
                Log.d("image4", String.valueOf(data.get("image4")));
                startActivity(intent);
            }
        });

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }*/
        /*// 구글 맵 실행
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);*/


    }

    /*@TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        String[] permissions = {
                // Manifest는 android를 import
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissions) {
            permissionCheck = this.checkSelfPermission(permission);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            this.requestPermissions(permissions, 1);
        }
    }*/

    private View.OnClickListener map_info_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            map_info_bar.setBackgroundColor(Color.BLACK);
            menu_bar.setBackgroundColor(Color.WHITE);
            review_bar.setBackgroundColor(Color.WHITE);
            //map_info.setVisibility(View.VISIBLE);
            review_screen.setVisibility(View.INVISIBLE);
        }
    };

    private View.OnClickListener menu_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            map_info_bar.setBackgroundColor(Color.WHITE);
            menu_bar.setBackgroundColor(Color.BLACK);
            review_bar.setBackgroundColor(Color.WHITE);
            //map_info.setVisibility(View.GONE);
            review_screen.setVisibility(View.INVISIBLE);
        }
    };

    private View.OnClickListener review_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            map_info_bar.setBackgroundColor(Color.WHITE);
            menu_bar.setBackgroundColor(Color.WHITE);
            review_bar.setBackgroundColor(Color.BLACK);
            //map_info.setVisibility(View.GONE);
            review_screen.setVisibility(View.VISIBLE);

        }
    };

    private View.OnClickListener review_write_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(MainActivity.LoginCheck()) {
                Intent intent = new Intent(RestaurantInfo.this, ReviewWriteActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                //finish();
            } else {
                Toast.makeText(RestaurantInfo.this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void GetReview() {
        String url = "http://ighook.cafe24.com/moamoa/GetReview.php";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String writer = jsonObject.getString("writer");
                        String content = jsonObject.getString("content");
                        String date = jsonObject.getString("date");
                        String eval1 = jsonObject.getString("eval1");
                        String eval2 = jsonObject.getString("eval2");
                        String eval3 = jsonObject.getString("eval3");
                        String eval4 = jsonObject.getString("eval4");
                        String image1 = jsonObject.getString("image1");
                        String image2 = jsonObject.getString("image2");
                        String image3 = jsonObject.getString("image3");
                        String image4 = jsonObject.getString("image4");

                        HashMap<String,String> r = new HashMap<String,String>();
                        r.put(TAG_WRITER, writer);
                        r.put(TAG_CONTENT, content);
                        r.put(TAG_DATE, date);
                        r.put(TAG_EVAL1, eval1);
                        r.put(TAG_EVAL2, eval2);
                        r.put(TAG_EVAL3, eval3);
                        r.put(TAG_EVAL4, eval4);
                        r.put("image1", image1);
                        r.put("image2", image2);
                        r.put("image3", image3);
                        r.put("image4", image4);
                        reviewList.add(r);
                    }
                    // 어댑터 생성, R.layout.list_item : Layout ID
                    ListAdapter adapter = new SimpleAdapter(
                            RestaurantInfo.this, reviewList, R.layout.review_list,
                            new String[]{TAG_WRITER,TAG_CONTENT,TAG_DATE},
                            new int[]{R.id.writer, R.id.content, R.id.date}
                    );
                    review_list.setAdapter(adapter); // ListView 에 어댑터 설정(연결)
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        GetReviewRequest getReviewRequest = new GetReviewRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RestaurantInfo.this);
        queue.add(getReviewRequest);
    }

    /*protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            review = jsonObj.getJSONArray(TAG_RESULTS);
            Log.d("showList", "try");
            for(int i = 0; i < review.length(); i++){
                JSONObject c = review.getJSONObject(i);
                String content = c.getString(TAG_CONTENT);
                String writer = c.getString(TAG_WRITER);
                String date = c.getString(TAG_DATE);

                HashMap<String,String> review = new HashMap<String,String>();
                review.put(TAG_CONTENT, content);
                review.put(TAG_WRITER, writer);
                review.put(TAG_DATE, date);

                reviewList.add(review);
            }

            // 어댑터 생성, R.layout.list_item : Layout ID
            ListAdapter adapter = new SimpleAdapter(
                RestaurantInfo.this, reviewList, R.layout.review_list,
                new String[]{TAG_CONTENT, TAG_WRITER, TAG_DATE},
                new int[]{R.id.content, R.id.writer, R.id.date}
            );

            review_list.setAdapter(adapter); // ListView 에 어댑터 설정(연결)
            Log.d("showList", "어댑터 생성");

        } catch (JSONException e) {
            Log.d("showList", "exception");
            e.printStackTrace();
        }
    }*/

    //@Override
    //public void onMapReady(GoogleMap googleMap) {
        //Log.d("onMapReady", "success");
        // 구글에서 등록한 api와 엮어주기
        // 시작위치를 서울 시청으로 변경
        /*LatLng cityHall = new LatLng(37.566622, 126.978159); // 서울시청 위도와 경도
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cityHall));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // 시작시 마커 생성하기
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(cityHall);
        markerOptions.title("시청");
        markerOptions.snippet("서울 시청");

        // 생성된 마커 옵션을 지도에 표시
        googleMap.addMarker(markerOptions);

        // 서울광장마커
        // 회사 DB에 데이터를 가지고 있어야 된다.
        LatLng plaza = new LatLng(37.565785, 126.978056);
        markerOptions.position(plaza);
        markerOptions.title("광장");
        markerOptions.snippet("서울 광장");
        googleMap.addMarker(markerOptions);

        //맵 로드 된 이후
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Toast.makeText(RestaurantInfo.this, "Map로딩성공", Toast.LENGTH_SHORT).show();
            }
        });*/

        /*//카메라 이동 시작
        googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                Log.d("set>>","start");
            }
        });*/

        /*// 카메라 이동 중
        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                Log.d("set>>","move");
            }
        });*/

        /*// 지도를 클릭하면 호출되는 이벤트
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // 기존 마커 정리
                googleMap.clear();
                // 클릭한 위치로 지도 이동하기
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // 신규 마커 추가
                MarkerOptions newMarker=new MarkerOptions();
                newMarker.position(latLng);
                googleMap.addMarker(newMarker);
            }
        });*/
    //}
}