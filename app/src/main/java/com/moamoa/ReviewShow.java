package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ReviewShow extends AppCompatActivity {

    String writer, date, content, eval1, eval2, eval3, eval4, image1, image2, image3, image4;
    TextView writer_text, date_text, content_text;
    ImageView image_view1, image_view2, image_view3, image_view4;
    String url;
    boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_show);

        writer = getIntent().getStringExtra("writer");
        content = getIntent().getStringExtra("content");
        date = getIntent().getStringExtra("date");

        eval1 = getIntent().getStringExtra("eval1");
        eval2 = getIntent().getStringExtra("eval2");
        eval3 = getIntent().getStringExtra("eval3");
        eval4 = getIntent().getStringExtra("eval4");

        image1 = getIntent().getStringExtra("image1");
        image2 = getIntent().getStringExtra("image2");
        image3 = getIntent().getStringExtra("image3");
        image4 = getIntent().getStringExtra("image4");

        Log.d("image1", image1);
        Log.d("image2", image2);
        Log.d("image3", image3);
        Log.d("image4", image4);

        writer_text = findViewById(R.id.writer);
        content_text = findViewById(R.id.content);
        date_text = findViewById(R.id.date);

        image_view1 = findViewById(R.id.image1);
        image_view2 = findViewById(R.id.image2);
        image_view3 = findViewById(R.id.image3);
        image_view4 = findViewById(R.id.image4);

        writer_text.setText(writer);
        content_text.setText(content);
        date_text.setText(date);

        url = "http://ighook.cafe24.com/moamoa/images/";

        SetImage();


        image_view1.setOnClickListener(image1_click);
        image_view2.setOnClickListener(image2_click);
        image_view3.setOnClickListener(image3_click);
        image_view4.setOnClickListener(image4_click);

        int image_view_width = image_view1.getWidth();
        Toast.makeText(this, String.valueOf(image_view_width), Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener image1_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(image1 != null) {
                Intent intent = new Intent(ReviewShow.this, ShowImage.class);
                intent.putExtra("url", url);
                intent.putExtra("image", image1);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener image2_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(image2 != null) {
                Intent intent = new Intent(ReviewShow.this, ShowImage.class);
                intent.putExtra("url", url);
                intent.putExtra("image", image2);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener image3_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(image3 != null) {
                Intent intent = new Intent(ReviewShow.this, ShowImage.class);
                intent.putExtra("url", url);
                intent.putExtra("image", image3);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener image4_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(image4 != null) {
                Intent intent = new Intent(ReviewShow.this, ShowImage.class);
                intent.putExtra("url", url);
                intent.putExtra("image", image4);
                startActivity(intent);
            }
        }
    };

    private void SetImage() {
        if(image1 != null) {
            Glide.with(this).load(url + image1).into(image_view1);
            image_view1.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }

        if(image2 != null) {
            Glide.with(this).load(url + image2).into(image_view2);
            image_view2.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }

        if(image3 != null) {
            Glide.with(this).load(url + image3).into(image_view3);
            image_view3.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
        if(image4 != null) {
            Glide.with(this).load(url + image4).into(image_view4);
            image_view4.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
    }
}