package com.moamoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.misc.AsyncTask;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.moamoa.MainActivity.ID;

public class ReviewWriteActivity extends AppCompatActivity {

    final int GET_GALLERY_IMAGE = 200;
    String name, address, telephone, nicName;
    EditText et_content;
    ImageView img1, img2, img3, img4;
    Button btn_writing;
    RatingBar r_eval1, r_eval2, r_eval3, r_eval4;
    String eval1, eval2, eval3, eval4;
    String img1_path, img2_path, img3_path, img4_path;
    String img1_type, img2_type, img3_type, img4_type;
    String fileName1, fileName2, fileName3, fileName4;

    int selectedImageSlot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        checkSelfPermission();

        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        telephone = getIntent().getStringExtra("telephone");
        //tedPermission();

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        r_eval1 = findViewById(R.id.eval1);
        r_eval2 = findViewById(R.id.eval2);
        r_eval3 = findViewById(R.id.eval3);
        r_eval4 = findViewById(R.id.eval4);
        et_content = findViewById(R.id.et_content);
        btn_writing = findViewById(R.id.btn_writing);

        // 업로드 버튼
        uploadImage();

        // 레이팅바 기본 값
        r_eval1.setRating(1);
        r_eval2.setRating(1);
        r_eval3.setRating(1);
        r_eval4.setRating(1);

        // 레이팅바 값을 가져옴
        getRating();

        // 리뷰 저장
        btn_writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterReview();
            }
        });
    }

    public void Upload(int num) {
        String content = et_content.getText().toString();
        String writer = MainActivity.nicName;
        //Log.d("nicName", MainActivity.nicName);
        String date = getDate();

        UploadToServer(num);

        String image1 = fileName1;
        String image2 = fileName2;
        String image3 = fileName3;
        String image4 = fileName4;

        String index = String.valueOf(num);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if(success) {
                        //clickUpload();
                        Toast.makeText(getApplicationContext(), "게시글을 성공적으로 저장하였습니다", Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent(ReviewWriteActivity.this, RestaurantInfo.class);
                        intent.putExtra("name", name);
                        startActivity(intent);*/
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "게시글을 작성하는데 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
                }
            }
        };
        ReviewWriteRequest reviewWriteRequest = new ReviewWriteRequest(index, name, content, writer, date, eval1, eval2, eval3, eval4, image1, image2, image3, image4, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReviewWriteActivity.this);
        queue.add(reviewWriteRequest);

    }

    public void RegisterReview() {
        String url = "http://ighook.cafe24.com/moamoa/CheckReviewIndex.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int num = jsonObject.getInt("index") + 1;
                    Upload(num);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    // 권한 확인
    private boolean hasPermissions() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED))
                return false;
        }
        return true;
    }

    // 날짜 가져오기
    public String getDate() {
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        // nowDate 변수에 값을 저장한다.
        return sdfNow.format(date);
    }

    // 점수 가져오기
    public void getRating() {
        r_eval1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                eval1 = String.valueOf(rating);
            }
        });

        r_eval2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                eval2 = String.valueOf(rating);
            }
        });

        r_eval3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                eval3 = String.valueOf(rating);
            }
        });

        r_eval4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                eval4 = String.valueOf(rating);
            }
        });
    }

    // 갤러리에서 이미지 가져오기
    public void uploadImage() {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                selectedImageSlot = 1;
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                selectedImageSlot = 2;
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                selectedImageSlot = 3;
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                selectedImageSlot = 4;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            switch (selectedImageSlot) {
                case 1:
                    img1.setImageURI(uri);
                    img1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img1_path = getRealPathFromUri(uri);
                    img1_type = img1_path.split("\\.")[1];
                    //new AlertDialog.Builder(ReviewWriteActivity.this).setMessage(img1_path).create().show();
                    break;
                case 2:
                    img2.setImageURI(uri);
                    img2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img2_path = getRealPathFromUri(uri);
                    img2_type = img2_path.split("\\.")[1];
                    //new AlertDialog.Builder(ReviewWriteActivity.this).setMessage(img2_path).create().show();
                    break;
                case 3:
                    img3.setImageURI(uri);
                    img3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img3_path = getRealPathFromUri(uri);
                    img3_type = img3_path.split("\\.")[1];
                    //new AlertDialog.Builder(ReviewWriteActivity.this).setMessage(img3_path).create().show();
                    break;
                case 4:
                    img4.setImageURI(uri);
                    img4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img4_path = getRealPathFromUri(uri);
                    img4_type = img4_path.split("\\.")[1];
                    //new AlertDialog.Builder(ReviewWriteActivity.this).setMessage(img4_path).create().show();
                    break;
            }
        }
    }

    // 퍼미션 확인
    public void checkSelfPermission() {
        String temp = "";
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (!TextUtils.isEmpty(temp)) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }
        else {
            // 모두 허용 상태
            //Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }

    // 절대 경로로 바꿔주는 함수
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();

        //String type = result.split("\\.")[1];
        //Toast.makeText(this, "이미지 타입 : " + type, Toast.LENGTH_SHORT).show();

        return result;
    }

    public void UploadToServer(int ReviewNumber) {
        //안드로이드에서 보낼 데이터를 받을 php 서버 주소
        String serverUrl = "http://ighook.cafe24.com/moamoa/ImageUpload.php";

        //파일 전송 요청 객체 생성 [결과를 String으로 받음]
        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //new AlertDialog.Builder(ReviewWriteActivity.this).setMessage("응답 : " + response).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReviewWriteActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //요청 객체에 보낼 데이터를 추가
        simpleMultiPartRequest.addStringParam("index", String.valueOf(ReviewNumber));

        if(!(img1_type == null)) {
            fileName1 = "Review_" + ReviewNumber + "_1." + img1_type;
            simpleMultiPartRequest.addStringParam("name1", fileName1);
            simpleMultiPartRequest.addFile("img1", img1_path);
        }

        if(!(img2_type == null)) {
            fileName2 = "Review_" + String.valueOf(ReviewNumber) + "_2." + img2_type;
            simpleMultiPartRequest.addStringParam("name2", fileName2);
            simpleMultiPartRequest.addFile("img2", img2_path);
        }

        if(!(img3_type == null)) {
            fileName3 = "Review_" + String.valueOf(ReviewNumber) + "_3." + img3_type;
            simpleMultiPartRequest.addStringParam("name3", fileName3);
            simpleMultiPartRequest.addFile("img3", img3_path);
        }

        if(!(img4_type == null)) {
            fileName4 = "Review_" + String.valueOf(ReviewNumber) + "_4." + img4_type;
            simpleMultiPartRequest.addStringParam("name4", fileName4);
            simpleMultiPartRequest.addFile("img4", img4_path);
        }

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(simpleMultiPartRequest);
    }
}