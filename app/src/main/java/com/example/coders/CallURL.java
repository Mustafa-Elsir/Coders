package com.example.coders;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mustafa on 06/05/2018.
 */

abstract class CallURL {
    static final String TAG = CallURL.class.getSimpleName();
    private String url ;
    private JSONObject params;
    private int method;
    CallURL(JSONObject params, String url, int method){
        this.url = url;
        this.params = params;
        this.method = method;
        Log.d(TAG, "params: "+params.toString());
    }

    void execute() {
        Log.d(TAG, "Loading products from the server...");
        String tag_string_req = "DoRequest";

        Log.d(TAG, "Method: "+method);
        Log.d(TAG, "URL: "+url);
        Log.d(TAG, "Params: "+params.toString());

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Thread.sleep(1500);
//                } catch (Exception e){
//                    e.printStackTrace();
//                    whenError(new VolleyError());
//                } finally {
//                    afterRequest(params);
//                }
//            }
//        });
//        thread.start();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response: " + response.toString());
                        afterRequest(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                whenError(error);
                Log.e(TAG, "Error on call GetContent api" + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    abstract void afterRequest(JSONObject response);
    abstract void whenError(VolleyError error);

}
