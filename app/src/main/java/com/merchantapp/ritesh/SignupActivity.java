package com.merchantapp.ritesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ritesh on 2/9/17.
 */

public class SignupActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    @BindView(R.id.mtspn_store_type)
    MaterialSpinner Mtspn_store_type;

    @BindView(R.id.mtspn_store_category)
    MaterialSpinner Mtspn_store_category;

    @BindView(R.id.mtspn_state)
    MaterialSpinner Mmtspn_state;

    @BindView(R.id.mtspn_city)
    MaterialSpinner Mtspn_city;

    @BindView(R.id.edt_business_name)
    EditText Edt_business_name;

    @BindView(R.id.edt_cp_name)
    EditText Edt_cp_name;

    @BindView(R.id.edt_mobile)
    EditText Edt_mobile;

    @BindView(R.id.edt_email)
    EditText Edt_email;

    @BindView(R.id.edt_address)
    EditText Edt_address;

    @BindView(R.id.edt_pincode)
    EditText Edt_pincode;

    @BindView(R.id.tv_signup_continue)
    TextView Tv_signup_continue;


    ArrayList<String> State_typelist = new ArrayList<String>();
    ArrayList<String> City_typelist = new ArrayList<String>();
    private static final String GET_STATE_LIST_DETAIL = "Area/getState";
    private static final String GET_CITY_LIST_DETAIL = "Area/getCity";


    String
            Str_Get_Control_Status = "",
            Str_Get_Control_Message = "",
            Str_Get_AllData_List = "",
            Str_Get_StateID = "",
            Str_Get_Mtsn_StateID = "",
            Str_Get_Mtsn_Statename = "",
            Str_Get_Cityid = "",
            Str_Get_Cityname = "",
            Str_Get_City_List_Status = "",
            Str_Get_City_List_Message = "",
            Str_Get_City_List_single_name = "",
            Str_Get_City_List_single_id = "",
            Str_Get_State_list_default,
            Str_Get_City_list_default,
            Str_Get_StateName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        AndroidNetworking.initialize(getApplicationContext());
        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        Get_State_List_Fast();

        TextView btn_signup_continue = (TextView) findViewById(R.id.tv_signup_continue);
        btn_signup_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent i = new Intent(SignupActivity.this, ForgotPasswordActivity.class);
//                startActivity(i);
//                finish();


            }
        });


//        Mtspn_places.setItems(USER_TYPE);
        Mmtspn_state.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                int posi = position;
                Str_Get_Mtsn_StateID = String.valueOf(posi);
                Str_Get_Mtsn_Statename = item;
                Log.e("posi ID:", "" + posi);
                Log.e("Str_Get_Mtsn_Statename:", "" + Str_Get_Mtsn_Statename);
                Log.e("Str_Get_Mtsn_StateID:", "" + Str_Get_Mtsn_StateID);

                Log.e("User_type Detail :", "Position :" + "" + position
                        + "\t" + "ID :" + "" + id
                        + "\t" + "Name :" + "" + item);


                Get_City_List_Fast();
            }
        });
        Mmtspn_state.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                android.support.design.widget.Snackbar.make(spinner, "Please Select Your State",
                        android.support.design.widget.Snackbar.LENGTH_SHORT).show();

            }
        });



        Mtspn_city.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                int posi = position;
                Str_Get_Cityid = String.valueOf(posi);
                Str_Get_Cityname = item;


                Log.e("posi ID:", "" + posi);
                Log.e("Str_Get_Cityid:", "" + Str_Get_Cityid);
                Log.e("Str_Get_Cityname:", "" + Str_Get_Cityname);


                Str_Get_Cityid = String.valueOf(posi);
                Log.e("Str_Get_Cityid :", "" + Str_Get_Cityid);

//                Log.e("User_type :", "" + User_type);
                Log.e("Cityname Detail :", "Position :" + "" + position
                        + "\t" + "ID :" + "" + id
                        + "\t" + "Name :" + "" + item);

                /*save value in sharepreference*/
                Appconstant.editor.putString("Cityname", Str_Get_Cityname);
                Appconstant.editor.putString("Cityid", Str_Get_Cityid);

                Appconstant.editor.commit();

            }
        });
        Mtspn_city.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                android.support.design.widget.Snackbar.make(spinner, "Please Select Your City",
                        android.support.design.widget.Snackbar.LENGTH_SHORT).show();

            }
        });





    }


    public final static boolean isValidEmaill(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private void Get_State_List_Fast() {

        Log.e("Get_State_List_Fast********************* :", "*************************");

        pDialog.setMessage("Fetching State List...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + GET_STATE_LIST_DETAIL)
                .setTag("Statelist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responsee) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response :", "" + responsee);

                        try {
                            State_typelist.add("Please select State");
                            JSONObject jobjresponse = new JSONObject(String.valueOf(responsee));
                            Log.e("jobjresponse List:", "" + jobjresponse);

                            JSONObject jobjControl = jobjresponse.getJSONObject("Control");
                            Str_Get_Control_Status = jobjControl.getString("Status");
                            Str_Get_Control_Message = jobjControl.getString("Message");


                            if (Str_Get_Control_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_Control_Status is:", "1");

                                Str_Get_Control_Message = jobjControl.getString("Message");
                                Log.e("Str_Get_Control_Message :", "" + Str_Get_Control_Message);


                                JSONArray jsonArray = jobjresponse.getJSONArray("Data");
                                Log.e("jsonArray List:", "" + jsonArray.toString());

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Str_Get_StateName = jsonObject.getString("StateName");
                                    Str_Get_StateID = jsonObject.getString("StateId");
                                    State_typelist.add(Str_Get_StateName);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item, State_typelist);
                                    Mmtspn_state.setItems(State_typelist);
                                    Str_Get_State_list_default = State_typelist.get(0);
                                    Log.e("Str_Get_State_list_default ", "" + Str_Get_State_list_default);

                                    Log.e(" State list result :", "" + State_typelist.size());
                                    Log.e("All City list Names:", "" + State_typelist
                                            + "\n" + "item 0 :" + "" + State_typelist.size());

                                }

                            } else {
//                                City_typelist.clear();
//                                Mtspn_city.setEnabled(false);
//                                Mtspn_city.setSelected(false);
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
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());
                    }
                });
    }


    private void Get_City_List_Fast() {

        Log.e("Get_City_List_Fast********************* :", "*************************");

        pDialog.setMessage("Fetching City List...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + GET_CITY_LIST_DETAIL)
                .addBodyParameter("StateId", Str_Get_Mtsn_StateID)

                .setTag("Citylist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responsee) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response :", "" + responsee);

                        try {
                            City_typelist.add("Please select City");
                            JSONObject jobjresponse = new JSONObject(String.valueOf(responsee));
                            Log.e("jobjresponse List:", "" + jobjresponse);

                            JSONObject jobjCityControl = jobjresponse.getJSONObject("Control");
                            Str_Get_City_List_Status = jobjCityControl.getString("Status");
                            Str_Get_City_List_Message = jobjCityControl.getString("Message");

                            Log.e("Str_Get_City_List_Status List:", "" + Str_Get_City_List_Status);
                            Log.e("Str_Get_City_List_Message :", "" + Str_Get_City_List_Message);


                            if (Str_Get_City_List_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_City_List_Status is:", "1");

                                JSONArray jsonCityArray = jobjresponse.getJSONArray("Data");
                                Log.e("jsonArray List:", "" + jsonCityArray.toString());

                                for (int i = 0; i < jsonCityArray.length(); i++) {

                                    JSONObject jsonObject = jsonCityArray.getJSONObject(i);
                                    Str_Get_City_List_single_name = jsonObject.getString("CityName");
                                    Str_Get_City_List_single_id = jsonObject.getString("CityId");
                                    City_typelist.add(Str_Get_City_List_single_name);

                                    Log.e("Str_Get_City_List_single_name :", "" + Str_Get_City_List_single_name);
                                    Log.e("Str_Get_City_List_single_id :", "" + Str_Get_City_List_single_id);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item, City_typelist);
                                    Mtspn_city.setItems(City_typelist);
                                    Str_Get_City_list_default = City_typelist.get(0);
                                    Log.e("Str_Get_City_list_default ", "" + Str_Get_City_list_default);

                                    Log.e(" City list result :", "" + City_typelist.size());
                                    Log.e("All City list Names:", "" + City_typelist
                                            + "\n" + "item 0 :" + "" + City_typelist.size());

                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("response AreaList:", "" + responsee);

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
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




}
