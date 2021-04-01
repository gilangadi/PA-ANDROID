package com.example.myapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapp.R;
import com.example.myapp.SharedPrefmanager;
import com.example.myapp.User;
import com.example.myapp.Utils.Api;
import com.example.myapp.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    String URLs = Api.BASE_URL + "/login";
    TextInputEditText editTextNim, editTextPassword;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefmanager.getInstance(this).isLoggedIn() && SharedPrefmanager.getInstance(this).getKeyStatus().equals("admin")) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else if (SharedPrefmanager.getInstance(this).isLoggedIn() && SharedPrefmanager.getInstance(this).getKeyStatus().equals("pengguna")) {
            Intent intent = new Intent(LoginActivity.this, PenggunaActivity.class);
            startActivity(intent);
            finish();
        }


        //// CEK KONEKSI INTERNET
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        editTextNim = (TextInputEditText) findViewById(R.id.nimlogin);
        editTextPassword = (TextInputEditText) findViewById(R.id.passwordlogin);
        SharedPrefmanager sharedPrefmanager;

        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
                {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                    } else {
                        Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //if user presses on not registered
        findViewById(R.id.tidakpunyakun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String nim = editTextNim.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(nim)) {
            editTextNim.setError("Please enter your username");
            editTextNim.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //getting the user from the response

                            JSONObject userJson = obj.getJSONObject("massage");

//                            Toast.makeText(getApplicationContext(), obj.getString("success"), Toast.LENGTH_SHORT).show();

                            //creating a new user object
                            User user = new User(
                                    userJson.getInt("id"),
                                    userJson.getString("nama"),
                                    userJson.getString("nim"),
                                    userJson.getString("prodi"),
                                    userJson.getString("status")
                            );

                            Toast.makeText(getApplicationContext(), obj.getString("loginberhasil"), Toast.LENGTH_SHORT).show();

                            SharedPrefmanager.getInstance(getApplicationContext()).userLogin(user);

//                            starting the profile activity
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            if (SharedPrefmanager.getInstance(getApplicationContext()).isLoggedIn() && SharedPrefmanager.getInstance(getApplicationContext()).getKeyStatus().equals("admin")) {
                                CheckTypesTask loading = new CheckTypesTask();
                                loading.execute();
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (SharedPrefmanager.getInstance(getApplicationContext()).isLoggedIn() && SharedPrefmanager.getInstance(getApplicationContext()).getKeyStatus().equals("pengguna")) {
                                CheckTypesTask loading = new CheckTypesTask();
                                loading.execute();
                                Intent intent = new Intent(LoginActivity.this, PenggunaActivity.class);
                                startActivity(intent);
                                finish();
                            }

//                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(LoginActivity.this);
        String typeStatus;

        @Override
        protected void onPreExecute() {
            //set message of the dialog
            asyncDialog.setMessage("mengambil data...");
            //show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            //don't touch dialog here it'll break the application
            //do some lengthy stuff like calling login webservice

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(LoginActivity.class.getSimpleName(), "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        Log.d(LoginActivity.class.getSimpleName(), token);

                        sendRegistrationToServer(token);
                    });

            return null;
        }

        private void sendRegistrationToServer(String token) {

            String Urls = Api.API_BASE_URL + "/savetoken";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls,
                    response -> {
                        Log.e("TOKEN", token);
                        int user_id = SharedPrefmanager.getInstance(getApplicationContext()).getId();
                        Log.i("USER ID",Integer.toString(user_id));

                        Log.i("URLS",Urls);
//                        try {
//
//                            JSONObject obj = new JSONObject(response);
//                            JSONObject userJson = obj.getJSONObject("msg");
//
//                            Toast.makeText(getApplicationContext(), obj.getString("loginberhasil"), Toast.LENGTH_SHORT).show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    int user_id = SharedPrefmanager.getInstance(getApplicationContext()).getId();
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", Integer.toString(user_id));
                    params.put("token", token);
                    return params;
                }
            };
            VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
        }

        @Override
        protected void onPostExecute(Void result) {
            //hide the dialog
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}