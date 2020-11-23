package com.moamoa;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardWriteRequest extends StringRequest {

    final static private String URL = "http://ighook.cafe24.com/moamoa/BoardWrite.php";
    private Map<String, String> map;

    public BoardWriteRequest(String index, String title, String content, String writer, String date, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("index", index);
        map.put("title", title);
        map.put("content", content);
        map.put("writer", writer);
        map.put("date", date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("안내", "로그인 됨");
        return map;
    }
}
