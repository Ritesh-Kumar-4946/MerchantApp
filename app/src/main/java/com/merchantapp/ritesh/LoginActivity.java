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
            Str_Get_Control_Status = "",
            Str_Get_Control_Message = "",
            strPut_mobile = "",
            Str_Get_MerchantId = "",
            Str_Get_MerchantCode = "",
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
                            JSONObject jobjControl = jobjresponse.getJSONObject("Control");
                            JSONObject jobjData = jobjresponse.getJSONObject("Data");
                            Str_Get_Control_Status = jobjControl.getString("Status");
                            Str_Get_Control_Message = jobjControl.getString("Message");
                            if (Str_Get_Control_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_Control_Status is:", "1");
                                Toast.makeText(LoginActivity.this, "" + Str_Get_Control_Message, Toast.LENGTH_LONG).show();

                                Str_Get_MerchantId = jobjData.getString("MerchantId");
                                Str_Get_MerchantCode = jobjData.getString("MerchantCode");


                                Log.e("Value save in Sharedpre", "ok");
                                Appconstant.editor.putString("id", Str_Get_MerchantId);
                                Appconstant.editor.putString("signupOk", "SignupTrue");
                                Appconstant.editor.putString("loginTest", "true");
                                Appconstant.editor.commit();


                                Intent Screen = new Intent(getApplicationContext(), VarifyMobileActivity.class);
                                startActivity(Screen);
                                finish();
                            } else {
                                Log.e("Str_Get_Control_Status is:", "0");
                                Toast.makeText(LoginActivity.this, "" + Str_Get_Control_Message, Toast.LENGTH_LONG).show();
                            }


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
