package com.moamoa;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetReviewRequest extends StringRequest {

    final static private String url = "http://ighook.cafe24.com/moamoa/GetReview.php";
    private Map<String, String> map;

    public GetReviewRequest(String name, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

        map = new HashMap<>();
        map.put("name", name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
