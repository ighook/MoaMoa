package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ShowImage extends AppCompatActivity {

    ImageView imageView;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        String url = getIntent().getStringExtra("url");
        String image = getIntent().getStringExtra("image");

        imageView = findViewById(R.id.image_view);
        btn_back = findViewById(R.id.btn_back);

        Glide.with(this).load(url + image).into(imageView);

        btn_back.setOnClickListener(btn_back_click);
    }

    private View.OnClickListener btn_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}