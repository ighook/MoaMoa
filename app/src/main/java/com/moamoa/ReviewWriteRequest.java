package com.moamoa;

import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewWriteRequest extends StringRequest {

    final static private String URL = "http://ighook.cafe24.com/moamoa/ReviewWrite.php";
    private Map<String, String> map;

    public ReviewWriteRequest(String index, String name, String content, String writer, String date,
                              String eval1, String eval2, String eval3, String eval4,
                              String image1, String image2, String image3, String image4,
                              Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("index", index);
        map.put("name", name);
        map.put("content", content);
        map.put("writer", writer);
        map.put("date", date);
        map.put("eval1", eval1);
        map.put("eval2", eval2);
        map.put("eval3", eval3);
        map.put("eval4", eval4);
        map.put("image1", image1);
        map.put("image2", image2);
        map.put("image3", image3);
        map.put("image4", image4);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
