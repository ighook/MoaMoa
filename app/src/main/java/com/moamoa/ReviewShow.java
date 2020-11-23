package com.moamoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ReviewShow extends AppCompatActivity {

    String nicName;
    String index, name, address, writer, date, content, eval1, eval2, eval3, eval4, image1, image2, image3, image4;
    LinearLayout review_manage;
    TextView writer_text, date_text, content_text;
    TextView eval1_text, eval2_text, eval3_text, eval4_text;
    ImageView image_view1, image_view2, image_view3, image_view4;
    Button btn_review_edit, btn_review_remove;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_show);

        review_manage = findViewById(R.id.review_manage);

        index = getIntent().getStringExtra("index");
        //new AlertDialog.Builder(ReviewShow.this).setMessage(index + "번 글").create().show();
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
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

        eval1_text = findViewById(R.id.eval1);
        eval2_text = findViewById(R.id.eval2);
        eval3_text = findViewById(R.id.eval3);
        eval4_text = findViewById(R.id.eval4);

        writer_text = findViewById(R.id.writer);
        content_text = findViewById(R.id.content);
        date_text = findViewById(R.id.date);

        image_view1 = findViewById(R.id.image1);
        image_view2 = findViewById(R.id.image2);
        image_view3 = findViewById(R.id.image3);
        image_view4 = findViewById(R.id.image4);

        btn_review_edit = findViewById(R.id.btn_review_edit);
        btn_review_remove = findViewById(R.id.btn_review_remove);

        writer_text.setText(writer);
        content_text.setText(content);
        date_text.setText(date);

        eval1_text.setText(eval1);
        eval2_text.setText(eval2);
        eval3_text.setText(eval3);
        eval4_text.setText(eval4);

        /*GetUserNicName();
        if(!writer.equals(nicName)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) review_manage.getLayoutParams();
            params.height = 0;
        }*/

        url = "http://ighook.cafe24.com/moamoa/images/";

        SetImage();

        image_view1.setOnClickListener(image1_click);
        image_view2.setOnClickListener(image2_click);
        image_view3.setOnClickListener(image3_click);
        image_view4.setOnClickListener(image4_click);
        btn_review_edit.setOnClickListener(review_edit_click);
        btn_review_remove.setOnClickListener(review_remove_click);
    }

    private View.OnClickListener image1_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!image1.equals("null")) {
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
            if(!image2.equals("null")) {
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
            if(!image3.equals("null")) {
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
            if(!image4.equals("null")) {
                Intent intent = new Intent(ReviewShow.this, ShowImage.class);
                intent.putExtra("url", url);
                intent.putExtra("image", image4);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener review_edit_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ReviewShow.this, ReviewEditActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("index", index);
            intent.putExtra("writer", writer);
            intent.putExtra("content", content);
            intent.putExtra("date", date);
            intent.putExtra("eval1", eval1);
            intent.putExtra("eval2", eval2);
            intent.putExtra("eval3", eval3);
            intent.putExtra("eval4", eval4);
            intent.putExtra("image1", image1);
            intent.putExtra("image2", image2);
            intent.putExtra("image3", image3);
            intent.putExtra("image4", image4);
            startActivity(intent);
        }
    };

    private View.OnClickListener review_remove_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private void SetImage() {
        if(!image1.equals("null")) {
            Glide.with(this).load(url + image1).into(image_view1);
            image_view1.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }

        if(!image2.equals("null")) {
            Glide.with(this).load(url + image2).into(image_view2);
            image_view2.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }

        if(!image3.equals("null")) {
            Glide.with(this).load(url + image3).into(image_view3);
            image_view3.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }

        if(!image4.equals("null")) {
            Glide.with(this).load(url + image4).into(image_view4);
            image_view4.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
    }

    private void GetUserNicName() {
        SharedPreferences sp = this.getSharedPreferences("Login",0);
        nicName = sp.getString("nicName", null);
    }
}