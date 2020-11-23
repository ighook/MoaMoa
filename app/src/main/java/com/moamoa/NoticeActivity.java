package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.misc.AsyncTask;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class NoticeActivity extends AppCompatActivity {

    TextView title_text, date_text, content_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_activiry);

        title_text = findViewById(R.id.titleText);
        content_text = findViewById(R.id.contentText);
        date_text = findViewById(R.id.dateText);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String date = getIntent().getStringExtra("date");

        title_text.setText(title);
        content_text.setText(content);
        date_text.setText(date);
    }


}