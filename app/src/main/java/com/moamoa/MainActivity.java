package com.moamoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_login, btn_search, btn_restaurant, btn_cafe;
    TextView detail_text;
    boolean login;
    String nicName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null) {
            login = (boolean) b.get("login");
            nicName = (String) b.get("nicName");
            if(login) {
                Toast.makeText(getApplicationContext(), nicName, Toast.LENGTH_SHORT).show();
            }
        }*/

        //s
        detail_text = findViewById(R.id.detail);
        btn_login = findViewById(R.id.btn_login);
        btn_search = findViewById(R.id.btn_search);
        btn_restaurant = findViewById(R.id.btn_restaurant);
        btn_cafe = findViewById(R.id.btn_cafe);

        // 로그인 버튼
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
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
        detail_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoticeListActivity.class);
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

    /*public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }*/
}