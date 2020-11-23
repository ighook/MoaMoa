package com.moamoa;

import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetMenuRequest extends StringRequest {

    final static private String url = "http://ighook.cafe24.com/moamoa/GetMenu.php";
    private Map<String, String> map;

    public GetMenuRequest(String name, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

        map = new HashMap<>();
        map.put("name", name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
