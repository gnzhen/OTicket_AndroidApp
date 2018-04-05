package com.example.gd.oticket.myrequest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.gd.oticket.Branch;
import com.example.gd.oticket.BranchRecyclerAdapter;
import com.example.gd.oticket.Queue;
import com.example.gd.oticket.Service;
import com.example.gd.oticket.BranchService;
import com.example.gd.oticket.LoginActivity;
import com.example.gd.oticket.MainActivity;
import com.example.gd.oticket.RegisterActivity;
import com.example.gd.oticket.ServiceRecyclerAdapter;
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
    private ArrayList<Branch> branches;
    private ArrayList<Service> services;
    private ArrayList<BranchService> branchServices;
    private Branch branch;
    private Queue queue;
    static int count;
    private Toast toast;

    public MyRequest(Context context) {
        this.ip = "http://192.168.0.120/OTicket/public/api/";
//        this.ip = "http://192.168.43.115/OTicket/public/api/";
//        this.ip = "http://192.168.0.202/OTicket/public/api/";

        this.context = context;
        this.branches = new ArrayList<>();
        this.services = new ArrayList<>();
        this.branchServices = new ArrayList<>();
        this.count = 10;
    }

    public void sendTokenToServer (final String userId, final String token, final VolleyCallback callback){
        String url = ip + "tokenToServer";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response tokenToServer", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("mobileUserId", userId);
                map.put("token", token);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


    public void register(final String name, final String email, final String phone, final String password,
                         final String passwordConfirm, final VolleyCallback callback) {

        String url = ip + "register";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                        Log.d("Volley response register", response);
                    try {
                        callback.onSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showVolleyError(error);
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

    public void login(final String email, final String password, final VolleyCallback callback){
        String url = ip + "login";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Volley response login", response);
                            try {
                                callback.onSuccess(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject jResponse =  new JSONObject(response);

                            if(jResponse.has("success")){
//                                showToast(jResponse.get("success").toString());

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
                        showVolleyError(error);
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

    public void issueTicket(final String userId, final String branchServiceId, final VolleyCallback callback){
        String url = ip + "issueTicket";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response issueTicket", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("mobileUserId", userId);
                map.put("branchServiceId", branchServiceId);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void cancelTicket(final String ticketId, final VolleyCallback callback){
        String url = ip + "cancelTicket";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response issueTicket", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("id", ticketId);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void postponeTicket(final String ticketId, final int time, final VolleyCallback callback){
        String url = ip + "postponeTicket";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response issueTicket", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("ticketId", ticketId);
                map.put("postponeTime", Integer.toString(time));

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getUserCurrentTickets(final String id, final VolleyCallback callback){
        String url = ip + "userCurrentTickets";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response userCurrentTickets", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());

                        if (error instanceof ServerError) {
                            showToast("Cannot connect to the server.");
                        }
                        else
                            showVolleyError(error);
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
    }

    public void getBranchServicesDetailsByBranchId(final String id, final VolleyCallback callback){
        String url = ip + "branchServicesDetailsByBranchId";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response userCurrentTickets", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
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
    }

    public void getTicketDetails(final String id, final VolleyCallback callback){
        String url = ip + "ticketDetails";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("volley response ticketDetails", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        if (error instanceof ServerError) {
                            showToast("Cannot connect to the server.");
                        }
                        else
                            showVolleyError(error);
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
    }

    public void getPostponeDetails(final String id, final VolleyCallback callback){
        String url = ip + "postponeDetails";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("volley response postponeDetails", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
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
    }

    public void getHistories(final String id, final VolleyCallback callback){
        String url = ip + "histories";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response histories", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
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
    }

    public void getBranches(final VolleyCallback callback){
        String url = ip + "branches";

        // Formulate the request and handle the response.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley response branches", response);
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                        showVolleyError(error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void showVolleyError(VolleyError error){
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            showToast("No internet connection");
        } else if (error instanceof AuthFailureError) {
            showToast("Authentication problem");
        } else if (error instanceof ServerError) {
            showToast("Opps! Please try again.");
        } else if (error instanceof NetworkError) {
            showToast("Network error");
        } else if (error instanceof ParseError) {
            showToast("Parse error");
        }
    }

    public void showToast(String text){
        toast = Toast.makeText(context ,text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.show();
    }

    public interface VolleyCallback{
        void onSuccess(String result) throws JSONException;
        void onFailure(String error);
    }
}
