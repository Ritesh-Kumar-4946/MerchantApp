package com.merchantapp.ritesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by ritesh on 11/9/17.
 */

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    String Sh_pre_User_ID = "",
            Str_Get_Control_Status = "",
            Str_Get_URL = "",
            postUrl = "",
            Str_Get_Control_Message = "";
    /*http://13.229.37.68/web-api/index.php/Area/getURL*/
    private static final String URL_DASHBOARD = "Area/getURL";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);
        dashboard();




        Log.e("PostURlllllll :" , "" + postUrl);
        webView = (WebView) findViewById(R.id.wv_link);
//        webView.loadUrl("https://www.google.co.in");
//        webView.setHorizontalScrollBarEnabled(false);


    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void dashboard() {
        final String user_id = Sh_pre_User_ID;

        pDialog.setMessage("Login.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain + URL_DASHBOARD,
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

                                Str_Get_URL = jobjData.getString("URL");
                                Log.e("URL :", "" + Str_Get_URL + Sh_pre_User_ID);
                                postUrl = Str_Get_URL + Sh_pre_User_ID;
                                Log.e("postUrl :", "" + postUrl);


                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.loadUrl(postUrl);



                            } else {
                                Log.e("Str_Get_Control_Status is:", "0");
                                Toast.makeText(WebViewActivity.this, "" + Str_Get_Control_Message, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(WebViewActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("MerchantCode", user_mobile);
//                params.put("Password", user_password);
                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
