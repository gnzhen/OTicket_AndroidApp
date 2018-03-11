package com.example.gd.oticket.myrequest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GD on 3/11/2018.
 */

public class MyRequest {
    private Context context;
    private RequestQueue queue;

public MyRequest(Context context, RequestQueue queue) {
    this.context = context;
    this.queue = queue;
}

public void register(final String username, final String email, final String password, final String passwordConfirm) {

    String url = "http://oticket.com/registerMobileUser";

    // Formulate the request and handle the response.
    StringRequest request = new StringRequest(Request.Method.POST, url,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response", response);
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("email", email);
                map.put("password", password);
                map.put("password_confirmation", passwordConfirm);

                return map;
            }
        };

        queue.add(request);

    }
}
