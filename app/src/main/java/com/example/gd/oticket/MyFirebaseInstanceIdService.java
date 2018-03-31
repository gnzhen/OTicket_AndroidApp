package com.example.gd.oticket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.gd.oticket.myrequest.MyRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by GD on 3/27/2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    //this method will be called
    //when the token is generated
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //now we will have the token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        saveTokenToPrefs(refreshedToken);
        sendTokenToServer(getApplicationContext().getSharedPreferences("auth", MODE_PRIVATE).getString("id", null), refreshedToken);

        //for now we are displaying the token in the log
        //copy it as this method is called only when the new token is generated
        //and usually new token is only generated when the app is reinstalled or the data is cleared
        Log.d("FCM_TOKEN", refreshedToken);
    }

    /**
     * @param token The new token.
     */
    public void sendTokenToServer(String userId, final String token) {
        MyRequest request = new MyRequest(this);

        /* Send token to server */
        request.sendTokenToServer(userId, token, new MyRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    if (jsonObject.has("success")) {

                        Log.d("Token To Server", jsonObject.get("fail").toString());
                    } else if (jsonObject.has("fail")) {
                        Log.d("Token To Server", jsonObject.get("fail").toString());
                    } else {
                        //Show first validation error from server
                        Iterator<String> keys = jsonObject.keys();
                        String str_Name = keys.next();
                        JSONArray value;
                        try {
                            value = jsonObject.getJSONArray(str_Name);
                            Log.e("Token to server", value.get(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d("onFailure", error);
            }
        });
    }

    public void saveTokenToPrefs(String _token)
    {
        // Access Shared Preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // Save to SharedPreferences
        editor.putString("registration_id", _token);
        editor.apply();
    }

    public String getTokenFromPrefs()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }
}
