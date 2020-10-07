package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class RegisterPersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_personal);
    }

    public void personal (View v) {
        Intent intent = new Intent(getBaseContext(), RegisterBusinessActivity.class);
        startActivity(intent);
    }
}