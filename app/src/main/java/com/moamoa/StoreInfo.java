package com.moamoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class StoreInfo extends AppCompatActivity implements OnMapReadyCallback {

    TextView name_text, map_info_bar, menu_bar, review_bar, info_text;
    Button btn_review_write;
    LinearLayout btn_map_info, btn_menu, btn_review, map_info_screen, menu_screen_sub;
    ListView review_list;
    RelativeLayout review_screen;
    ScrollView menu_screen;
    double latitude, longitude;
    String name, address, index;
    String myJSON;
    JSONArray review;
    ArrayList<HashMap<String, String>> reviewList = new ArrayList<>();

    private GoogleMap mMap;

    public static Activity activity;

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
        setContentView(R.layout.activity_store_info);

        activity = StoreInfo.this;

        // list id
        review_list = findViewById(R.id.review_list);

        // screen id
        map_info_screen = findViewById(R.id.map_info_screen);
        menu_screen = findViewById(R.id.menu_screen);
        review_screen = findViewById(R.id.review_screen);
        menu_screen_sub = findViewById(R.id.menu_screen_sub);
        info_text = findViewById(R.id.info);

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

        // 정보
        String info = getIntent().getStringExtra("info");
        info_text.setText(info);

        // 위치
        address = getIntent().getStringExtra("address");


        // 클릭 리스너
        btn_map_info.setOnClickListener(map_info_click);
        btn_menu.setOnClickListener(menu_click);
        btn_review.setOnClickListener(review_click);
        btn_review_write.setOnClickListener(review_write_click);

        // 리뷰 레이아웃 비활성화
        review_screen.setVisibility(View.INVISIBLE);

        // 리뷰 불러옴
        GetReview();

        // 메뉴 불러옴
        GetMenu();

        // 리뷰 선택
        review_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> data = reviewList.get(position);
                Intent intent = new Intent(StoreInfo.this, ReviewShow.class);
                intent.putExtra("index", data.get("index"));
                intent.putExtra("name", name);
                intent.putExtra("address", address);
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

        // 구글 맵
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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
            map_info_screen.setVisibility(View.VISIBLE);
            menu_screen.setVisibility(View.GONE);
            review_screen.setVisibility(View.GONE);
        }
    };

    // 메뉴 탭
    private View.OnClickListener menu_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            map_info_bar.setBackgroundColor(Color.WHITE);
            menu_bar.setBackgroundColor(Color.BLACK);
            review_bar.setBackgroundColor(Color.WHITE);
            map_info_screen.setVisibility(View.GONE);
            menu_screen.setVisibility(View.VISIBLE);
            review_screen.setVisibility(View.GONE);
        }
    };

    // 리뷰 탭
    private View.OnClickListener review_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            map_info_bar.setBackgroundColor(Color.WHITE);
            menu_bar.setBackgroundColor(Color.WHITE);
            review_bar.setBackgroundColor(Color.BLACK);
            map_info_screen.setVisibility(View.GONE);
            menu_screen.setVisibility(View.GONE);
            review_screen.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener review_write_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(MainActivity.LoginCheck()) {
                Intent intent = new Intent(StoreInfo.this, ReviewWriteActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                startActivity(intent);
                //finish();
            } else {
                Toast.makeText(StoreInfo.this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void GetReview() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String index = jsonObject.getString("num");
                        String writer = jsonObject.getString("writer");
                        String content = jsonObject.getString("content");
                        String date = jsonObject.getString("date");

                        String eval1 = jsonObject.getString("eval1");
                        String eval2 = jsonObject.getString("eval2");
                        String eval3 = jsonObject.getString("eval3");
                        String eval4 = jsonObject.getString("eval4");
                        String eval_ave = String.valueOf((Float.parseFloat(eval1)+ Float.parseFloat(eval2) + Float.parseFloat(eval3) + Float.parseFloat(eval4)) / 4);

                        String image1 = jsonObject.getString("image1");
                        String image2 = jsonObject.getString("image2");
                        String image3 = jsonObject.getString("image3");
                        String image4 = jsonObject.getString("image4");

                        HashMap<String,String> r = new HashMap<String,String>();
                        r.put("index", index);
                        r.put(TAG_WRITER, writer);
                        r.put(TAG_CONTENT, content);
                        r.put(TAG_DATE, date);
                        r.put(TAG_EVAL1, eval1);
                        r.put(TAG_EVAL2, eval2);
                        r.put(TAG_EVAL3, eval3);
                        r.put(TAG_EVAL4, eval4);
                        r.put("eval_ave", eval_ave);
                        r.put("image1", image1);
                        r.put("image2", image2);
                        r.put("image3", image3);
                        r.put("image4", image4);
                        reviewList.add(r);
                    }
                    // 어댑터 생성, R.layout.list_item : Layout ID
                    ListAdapter adapter = new SimpleAdapter(
                            StoreInfo.this, reviewList, R.layout.review_list,
                            new String[]{TAG_WRITER,TAG_CONTENT,TAG_DATE, "eval_ave"},
                            new int[]{R.id.writer, R.id.content, R.id.date, R.id.eval}
                    );
                    review_list.setAdapter(adapter); // ListView 에 어댑터 설정(연결)
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        GetReviewRequest getReviewRequest = new GetReviewRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(StoreInfo.this);
        queue.add(getReviewRequest);
    }

    public void GetMenu() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<String> typeList = new ArrayList<>();
                    ArrayList<Menu> arrayList = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> iterator = jsonObject.keys();

                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        JSONArray jsonArray = jsonObject.getJSONArray(key);

                        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = layoutInflater.inflate(R.layout.menu_header,null);
                        TextView textView = view.findViewById(R.id.type);
                        textView.setText(key);
                        menu_screen_sub.addView(view);

                        typeList.add(key);
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject menuObject = jsonArray.getJSONObject(i);

                            View view2 = layoutInflater.inflate(R.layout.menu_body,null);
                            TextView food_name_text = view2.findViewById(R.id.food_name);
                            TextView food_price_text = view2.findViewById(R.id.food_price);
                            ImageView food_image = view2.findViewById(R.id.food_image);

                            String menu = menuObject.getString("menu");
                            String price = menuObject.getString("price");

                            String url = "http://ighook.cafe24.com/moamoa/food/" + name + "/" + menu + ".jpg";
                            Glide.with(view2).load(url).error(R.drawable.image_none).into(food_image);

                            food_name_text.setText(menu);
                            food_price_text.setText(price);
                            menu_screen_sub.addView(view2);

                            arrayList.add(new Menu(name, menu, price));
                        }
                    }
                    } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        };
        GetMenuRequest getMenuRequest = new GetMenuRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(StoreInfo.this);
        queue.add(getMenuRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double latitude, longitude;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = null;
        try {
            list = geocoder.getFromLocationName(address, 1);
            Log.d("위치", String.valueOf(list));

            latitude = list.get(0).getLatitude();
            longitude = list.get(0).getLongitude();

            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(name);

            Marker marker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            marker.showInfoWindow();
        } catch (IOException e) {
            //Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
            Log.d("위치", String.valueOf(e));
            e.printStackTrace();
        }
    }
}