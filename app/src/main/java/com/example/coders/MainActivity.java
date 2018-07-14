package com.example.coders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    LinearLayout calender, attendee, profile, epay, logout;
    SharedPreferences preferences;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        calender = (LinearLayout) findViewById(R.id.calendar);
        attendee = (LinearLayout) findViewById(R.id.attendee);
        profile = (LinearLayout) findViewById(R.id.profile);
        epay = (LinearLayout) findViewById(R.id.epay);
        logout = (LinearLayout) findViewById(R.id.logout);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            }
        });

        attendee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AttendeeActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        epay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EpayActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = new JSONObject();
                    String token = preferences.getString("token", null);
//                    if(token == null){
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                        finish();
//                        return;
//                    }
                    object.put("token", token);
                    dialog.setMessage("Logging out...");
                    dialog.show();
                    CallURL callURL = new CallURL(object, AppConfig.logoutUrl, Request.Method.POST) {
                        @Override
                        void afterRequest(JSONObject response) {
                            Log.d(TAG, response.toString());
                            dialog.dismiss();
                            preferences.edit().remove("token").apply();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }

                        @Override
                        void whenError(VolleyError error) {
                            dialog.dismiss();
                            try {
                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null && networkResponse.data != null) {
                                    Log.e(TAG, new String(networkResponse.data));
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    callURL.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
