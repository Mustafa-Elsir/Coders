package com.example.coders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    static final String TAG = LoginActivity.class.getSimpleName();

    EditText email, password;
    Button submit;
    ProgressDialog dialog;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit = (Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("email", email.getText().toString());
                    object.put("password", password.getText().toString());
                    dialog.setMessage("Logging in...");
                    dialog.show();
//                    Thread thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                                try{
//                                    Thread.sleep(1500);
//                                } catch (Exception e){
//                                    e.printStackTrace();
//                                } finally {
//                                    dialog.dismiss();
//                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                    finish();
//                                }
//
//                        }
//                    });
//                    thread.start();
                    CallURL callURL = new CallURL(object, AppConfig.loginUrl, Request.Method.GET) {
                        @Override
                        void afterRequest(JSONObject response) {
                            Log.d(TAG, response.toString());
                            dialog.dismiss();
//                            try {
//                                String token = response.get("token").toString();
//                                preferences.edit().putString("token", token).apply();
//                            } catch (Exception e){
//                                e.printStackTrace();
//                            }
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        void whenError(VolleyError error) {
                            dialog.dismiss();
                            NetworkResponse networkResponse = error.networkResponse;
                            if(networkResponse != null && networkResponse.data != null){
                                Log.e(TAG,  new String(networkResponse.data));
                            }
                        }
                    };
                    callURL.execute();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
