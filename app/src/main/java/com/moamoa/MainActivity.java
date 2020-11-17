package com.moamoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_login, btn_search, btn_restaurant, btn_cafe;
    TextView detail_text;
    boolean login;

    static String ID = null;
    static String PW = null;
    static String nicName = null;

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
        GetUserInfo();

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

    private void GetUserInfo() {
        SharedPreferences sp1 = this.getSharedPreferences("Login",0);
        ID = sp1.getString("ID", null);
        PW = sp1.getString("PW", null);
        nicName = sp1.getString("nicName", null);
    }

    public static boolean LoginCheck() {
        return ID != null;
    }

    private void Logout() {
        SharedPreferences sp1 = this.getSharedPreferences("Login",0);
        SharedPreferences.Editor Ed = sp1.edit();
        Ed.remove("Login");
        Ed.apply();
        ID = null;
        PW = null;
        nicName = null;
    }
}