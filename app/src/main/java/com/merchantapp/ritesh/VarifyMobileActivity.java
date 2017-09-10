package com.merchantapp.ritesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ritesh on 2/9/17.
 */

public class VarifyMobileActivity extends AppCompatActivity {


    @BindView(R.id.edt_otp)
    EditText Edt_otp;

    @BindView(R.id.tv_btn_varification)
    TextView Tv_btn_varification;

    @BindView(R.id.tv_mobile_number)
    TextView tv_mobile_number;

    String Str_OTP = "1001";

    private ProgressDialog pDialog;

    private static final String VARIFYOTP = "Store/validateOTP";

    String Sh_pre_User_ID = "",
            Str_Get_Control_Status = "",
            Str_Get_Mobile_Shrd = "",
            Str_Get_Control_Message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_varification);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Str_Get_Mobile_Shrd = Appconstant.sh.getString("id", null);
        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Tv_btn_varification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean iserror = false;
                Str_OTP = Edt_otp.getText().toString().trim();
                Log.e("OTP :", "" + Str_OTP);

                if (Str_OTP.equalsIgnoreCase("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_otp);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please Enter OTP", Toast.LENGTH_SHORT).show();


                }

                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    Log.e("OK Valid :", "OOOO");
                    VarifyOTP();


                }


            }
        });


    }


    private void VarifyOTP() {

        Log.e("VarifyOTP********************* :", "*************************");

        pDialog.setMessage("Wait ...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + VARIFYOTP)
                .addBodyParameter("MerchantId", Sh_pre_User_ID)
                .addBodyParameter("OTP", Str_OTP)
                .setTag("Storelist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responsee) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response :", "" + responsee);

                        try {

                            JSONObject jobjresponse = new JSONObject(String.valueOf(responsee));
                            Log.e("jobjresponse :", "" + jobjresponse);

                            JSONObject jobjControl = jobjresponse.getJSONObject("Control");
                            Str_Get_Control_Status = jobjControl.getString("Status");
                            Str_Get_Control_Message = jobjControl.getString("Message");


                            if (Str_Get_Control_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_Control_Status is:", "1");

                                Str_Get_Control_Message = jobjControl.getString("Message");
                                Log.e("Str_Get_Control_Message :", "" + Str_Get_Control_Message);

                                Log.e("Varify :", "GOOD");

                                Toast.makeText(VarifyMobileActivity.this, "Varification Complete", Toast.LENGTH_SHORT).show();


                                Appconstant.editor.putString("varificationOk", "VarificationTrue");
                                Appconstant.editor.commit();


                                Intent GoMobileVarificationScreen = new Intent(VarifyMobileActivity.this, ImageUpload.class);
                                startActivity(GoMobileVarificationScreen);
                                finish();


                            } else {
                                Log.e("Varify :", "ERROR");
                                Toast.makeText(VarifyMobileActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("response ListList:", "" + responsee);

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Toast.makeText(VarifyMobileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        Log.e("Error Response :", "" + error.toString());
                    }
                });
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
