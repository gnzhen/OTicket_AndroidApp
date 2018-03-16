package com.example.gd.oticket.myrequest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.gd.oticket.Branch;
import com.example.gd.oticket.Queue;
import com.example.gd.oticket.Service;
import com.example.gd.oticket.BranchService;
import com.example.gd.oticket.LoginActivity;
import com.example.gd.oticket.MainActivity;
import com.example.gd.oticket.RegisterActivity;
import com.example.gd.oticket.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by GD on 3/11/2018.
 */

public class MyRequest {
    private Context context;
    private String ip;
    private Toast toast;
    private ArrayList<Branch> branches;
    private ArrayList<Service> services;
    private ArrayList<BranchService> branchServices;
    private Branch branch;
    private Queue queue;
    static int count;

    public MyRequest(Context context) {
        this.ip = "http://192.168.43.115/OTicket/public/api/";
        this.context = context;
        this.branches = new ArrayList<>();
        this.services = new ArrayList<>();
        this.branchServices = new ArrayList<>();
        this.count = 10;
    }

    public void register(final String name, final String email, final String phone, final String password, final String passwordConfirm) {

        String url = ip + "register";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d("My App volley response", response);

                        JSONObject jResponse =  new JSONObject(response);

                        if(jResponse.has("success")){
                            showToast(jResponse.get("success").toString());

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);

                        } else {
                            //Show first validation error from server
                            Iterator<String> keys = jResponse.keys();
                            String str_Name = keys.next();
                            JSONArray value = jResponse.getJSONArray(str_Name);
                            showToast(value.get(0).toString());
                        }

                    } catch (Throwable t) {
                        showToast("Something wrong!");
                        Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showToast("Something wrong!");
                    Log.d("Volley Error", error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("phone", phone);
                    map.put("password", password);
                    map.put("password_confirmation", passwordConfirm);

                    return map;
                }
            };

            VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void login(final String email, final String password){
        String url = ip + "login";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("My App volley response", response);

                            JSONObject jResponse =  new JSONObject(response);

                            if(jResponse.has("success")){
                                showToast(jResponse.get("success").toString());

                            } else {
                                //Show first validation error from server
                                Iterator<String> keys = jResponse.keys();
                                String str_Name = keys.next();
                                JSONArray value = jResponse.getJSONArray(str_Name);
                                showToast(value.get(0).toString());
                            }

                        } catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Something wrong!");
                        Log.d("Volley Error", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public ArrayList<Branch> getBranches(final VolleyCallback callback){
        String url = ip + "branches";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("My App volley response", response);

                        callback.onSuccess(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showToast("Some error occured!");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);

        return branches;
    }

    public ArrayList<Service> getServices(){
        String url = ip + "services";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("My App volley response", response);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(jsonArray != null){
                            branches.clear();
                            for(int i = 0; i < jsonArray.length(); i++){
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Service service = new Service(
                                            jsonObject.get("id").toString(),
                                            jsonObject.get("code").toString(),
                                            jsonObject.get("name").toString()
                                    );
                                    services.add(service);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        count = count - 1;
                        Log.d("Volley Error", error.toString());
                        if (error instanceof TimeoutError && count > 0) {
                            Log.d("Retry request", Integer.toString(count));
                            // note : may cause recursive invoke if always timeout.
                            getServices();
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);

        return services;
    }

    public ArrayList<BranchService> getBranchServices(){
        String url = ip + "branchServices";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("My App volley response", response);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(jsonArray != null){
                            branchServices.clear();
                            for(int i = 0; i < jsonArray.length(); i++){
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    BranchService branchService = new BranchService(
                                            jsonObject.get("id").toString(),
                                            jsonObject.get("branchId").toString(),
                                            jsonObject.get("serviceId").toString(),
                                            (Integer)jsonObject.get("avgWaitTime")
                                    );
                                    branchServices.add(branchService);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        count = count - 1;
                        Log.d("Volley Error", error.toString());
                        if (error instanceof TimeoutError && count > 0) {
                            Log.d("Retry request", Integer.toString(count));
                            // note : may cause recursive invoke if always timeout.
                            getBranchServices();
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);

        return branchServices;
    }

    public ArrayList<BranchService> getBranchServicesByBranchId(final String id){
        String url = ip + "branchServicesByBranchId";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("My App volley response", response);
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(jsonArray != null){
                            branchServices.clear();
                            for(int i = 0; i < jsonArray.length(); i++){
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    BranchService branchService = new BranchService(
                                            jsonObject.get("id").toString(),
                                            jsonObject.get("branchId").toString(),
                                            jsonObject.get("serviceId").toString(),
                                            (Integer)jsonObject.get("avgWaitTime")
                                    );
                                    branchServices.add(branchService);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        count = count - 1;
                        Log.d("Volley Error", error.toString());
                        if (error instanceof TimeoutError && count > 0) {
                            Log.d("Retry request", Integer.toString(count));
                            // note : may cause recursive invoke if always timeout.
                            getBranchServices();
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id", id);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);

        return branchServices;
    }

    public Queue getQueueByBranchServiceId(final String id){
        String url = ip + "queueByBranchServiceId";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("My App volley response", response);
                        JSONObject jsonObject;
                        queue = null;
                        try {
                            jsonObject = new JSONObject(response);

                            if(jsonObject != null){
                                queue = new Queue(
                                        (long)jsonObject.get("id"),
                                        jsonObject.get("branchServiceId").toString(),
                                        (Integer)jsonObject.get("ticketServingNow"),
                                        (Integer)jsonObject.get("waitTime"),
                                        (Integer)jsonObject.get("pendingTicket")
                                );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        count = count - 1;
                        Log.d("Volley Error", error.toString());
                        if (error instanceof TimeoutError && count > 0) {
                            Log.d("Retry request", Integer.toString(count));
                            // note : may cause recursive invoke if always timeout.
                            getBranchServices();
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id", id);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);

        return queue;
    }

    public void showToast(String text){
        toast = Toast.makeText(context ,text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.show();
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }
}
