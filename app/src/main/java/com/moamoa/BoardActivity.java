package com.moamoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BoardActivity extends AppCompatActivity {

    TextView title_text, date_text, content_text, writer_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        title_text = findViewById(R.id.titleText);
        writer_text = findViewById(R.id.writerText);
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