package com.merchantapp.ritesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ritesh on 2/9/17.
 */

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.tv_login)
    TextView btn_login;

    @BindView(R.id.tv_newuser_register)
    TextView Tv_newuser_register;

    @BindView(R.id.tv_forget_password)
    TextView Tv_forget_password;

    @BindView(R.id.edt_login_mobile)
    EditText Edt_login_mobile;

    @BindView(R.id.edt_password)
    EditText Edt_password;


    private ProgressDialog pDialog;

    String
            strPut_mobile = "",
            StrGet_status = "",
            strPut_password = "";

    private static final String LOGIN_URL = "Store/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        Tv_newuser_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoGetProfileScreen = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(GoGetProfileScreen);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean iserror = false;

                strPut_mobile = Edt_login_mobile.getText().toString().trim();
                strPut_password = Edt_password.getText().toString().trim();

                Log.e(" Sign Up Fields data :", "\n"
                        + "strPut_mobile :" + "" + strPut_mobile + "\n"
                        + "strPut_password :" + "" + strPut_password + "\n");


                if (strPut_mobile.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_login_mobile);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();


                } else if (strPut_password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Password", Toast.LENGTH_SHORT).show();

                } else if (strPut_password.length() < 5) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Please enter at least 5 character in password.",
                            Toast.LENGTH_SHORT).show();


                } else if (strPut_password.contains(" ")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Space is not allowed in password.",
                            Toast.LENGTH_SHORT).show();


                }
                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                    userLogin();


                }


//                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
//                startActivity(i);
//                finish();
            }
        });
    }

    public final static boolean isValidEmaill(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private void userLogin() {
        final String user_mobile = strPut_mobile;
        final String user_password = strPut_password;

        Log.e("Volley userLogin Data :", ""
                + "\n" + "user_mobile :" + "" + user_mobile
                + "\n" + "user_password :" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Login.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain + LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();
                        hideDialog();
                        Log.e("Response :", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
                            Log.e("jobjresponse :", "" + jobjresponse);
                            Log.e("response :", "" + response);

//                            StrGet_status = jobjresponse.getString("status");
//                            StrGet_message = jobjresponse.getString("message");
//                            Log.e("StrGet_message is:", "" + StrGet_message);
//
//                            if (StrGet_status.equalsIgnoreCase("1")) {
//                                Log.e("StrGet_status is:", "1");
//                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
//                                Log.e("jobjresult :", "" + jobjresult);
//
//                                StrGet_user_id = jobjresult.getString("id");
//                                StrGet_user_name = jobjresult.getString("username");
//                                StrGet_user_mobile = jobjresult.getString("mobile");
//                                StrGet_user_email = jobjresult.getString("email");
//                                StrGet_user_active_status = jobjresult.getString("status");
//                                StrGet_business_status = jobjresult.getString("business_status");
//                                StrGet_business_id = jobjresult.getString("business_id");
//                                StrGet_home_address = jobjresult.getString("home_address");
//                                StrGet_work_address = jobjresult.getString("work_address");
//
//                                Log.e("StrGet_status is:", "1");
//                                Log.e("StrGet_user_id is:", "" + StrGet_user_id);
//                                Log.e("StrGet_user_name is:", "" + StrGet_user_name);
//                                Log.e("StrGet_user_mobile is:", "" + StrGet_user_mobile);
//                                Log.e("StrGet_user_email is:", "" + StrGet_user_email);
//                                Log.e("StrGet_user_active_status is:", "" + StrGet_user_active_status);
//
//                                Appconstant.editor.putString("id", StrGet_user_id);
//                                Appconstant.editor.putString("email", StrGet_user_email);
//                                Appconstant.editor.putString("username", StrGet_user_name);
//                                Appconstant.editor.putString("mobile", StrGet_user_mobile);
//                                Appconstant.editor.putString("loginTest", "true");
//                                Appconstant.editor.commit();
//
//
//                                if (!StrGet_business_id.equalsIgnoreCase("0")) {
//                                    Log.e("StrGet_business_id is:", "" + StrGet_business_id);
//                                } else {
//                                    Log.e("StrGet_business_id is:", "" + "0");
//                                }
//
//                                if (!StrGet_work_address.equalsIgnoreCase("")) {
//                                    Log.e("StrGet_work_address is:", "" + StrGet_work_address);
//                                } else {
//                                    Log.e("StrGet_work_address is:", "" + "Not Found");
//                                }
//
//                                if (!StrGet_home_address.equalsIgnoreCase("")) {
//                                    Log.e("StrGet_home_address is:", "" + "Found So go to Get profile screen");
//                                    Log.e("StrGet_home_address is:", "" + StrGet_home_address);
//                                    Intent GoGetProfileScreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
//                                    startActivity(GoGetProfileScreen);
//                                    finish();
//                                } else {
//                                    Log.e("StrGet_home_address is:", "" + "Not Found So go to create profile screen");
//
//                                    Intent GoGetProfileScreen = new Intent(getApplicationContext(), ProfileCreateActivity.class);
//                                    startActivity(GoGetProfileScreen);
//                                    finish();
//                                }
//
//                            } else if (StrGet_status.equalsIgnoreCase("0")) {
//                                Log.e("StrGet_status is:", "0");
//                                Log.e("Login Detail Not Match :", "Error");
//
////                            Toast.makeText(LoginActivity.this, "User detail not found", Toast.LENGTH_LONG).show();
//
//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MerchantCode", user_mobile);
                params.put("Password", user_password);
                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
