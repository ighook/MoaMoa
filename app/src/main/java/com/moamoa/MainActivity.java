package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView ID, PW;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);

        ID = findViewById(R.id.id);
        PW = findViewById(R.id.password);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPW = intent.getStringExtra("userPW");

        ID.setText(userID);
        PW.setText(userPW);
    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}