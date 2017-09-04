package com.merchantapp.ritesh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
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

    @BindView(R.id.edt_password)
    EditText Edt_password;

    @BindView(R.id.edt_pincode)
    EditText Edt_pincode;

    @BindView(R.id.tv_signup_continue)
    TextView Tv_signup_continue;


    ArrayList<String> State_typelist = new ArrayList<String>();
    ArrayList<String> City_typelist = new ArrayList<String>();
    ArrayList<String> Category_typelist = new ArrayList<String>();
    ArrayList<String> StoreCategory_typelist = new ArrayList<String>();
    private static final String GET_STORE_CATEGORY_LIST_DETAIL = "Category/getStoreCategory";
    private static final String SIGNUP = "Store/signUp";
    private static final String GET_STORE_LIST_DETAIL = "Category/getCategory";
    private static final String GET_STATE_LIST_DETAIL = "Area/getState";
    private static final String GET_CITY_LIST_DETAIL = "Area/getCity";


    String
            Deviceid = "",
            Str_Get_Control_Status = "",
            Str_Get_StoreControl_Status = "",
            Str_Get_StoreControl_Message = "",
            Str_Get_Control_Message = "",
            Str_Get_AllData_List = "",
            Str_Get_StateID = "",
            Str_Get_Mtsn_CategoryID = "",
            Str_Get_Mtsn_Categoryname = "",
            Str_Get_Mtsn_StoreCategoryID = "",
            Str_Get_Mtsn_StoreCategoryname = "",
            Str_Get_Mtsn_StateID = "",
            Str_Get_Mtsn_Statename = "",
            Str_Get_Cityid = "",
            Str_Get_Cityname = "",
            Str_Get_City_List_Status = "",
            Str_Get_City_List_Message = "",
            Str_Get_City_List_single_name = "",
            Str_Get_City_List_single_id = "",
            Str_Get_StoreCategoryName = "",
            Str_Get_StoreCategoryID = "",
            Str_Get_CategoryName = "",
            Str_Get_CategoryID = "",
            Str_Get_CategoryIcon = "",
            Str_Get_State_list_default,
            Str_Get_StoreCategory_list_default,
            Str_Get_Category_list_default,
            Str_Get_City_list_default,
            Str_Get_Business_Name = "",
            Str_Get_Persone_Name = "",
            Str_Get_Persone_Mobile = "",
            Str_Get_Persone_Email = "",
            Str_Get_Persone_Address = "",
            Str_Get_Persone_Password = "",
            Str_Get_Persone_Pincode = "",
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
        Get_Store_List_Fast();
        Get_Store_Category_List_Fast();
        Get_State_List_Fast();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Deviceid = telephonyManager.getDeviceId();
        String Deviceidd = telephonyManager.getDeviceId().toString();
        String Hell = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("Device ID :", "" + Deviceid);
        Log.e("Deviceidd ID :", "" + Deviceidd);
        Log.e("Hell ID :", "" + Hell.toString());
        Log.e("Hell IDd :", "" + Hell);

        TextView btn_signup_continue = (TextView) findViewById(R.id.tv_signup_continue);
        btn_signup_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean iserror = false;

                Str_Get_Business_Name = Edt_business_name.getText().toString().trim();
                Str_Get_Persone_Name = Edt_cp_name.getText().toString().trim();
                Str_Get_Persone_Mobile = Edt_mobile.getText().toString().trim();
                Str_Get_Persone_Email = Edt_email.getText().toString().trim();
                Str_Get_Persone_Address = Edt_address.getText().toString().trim();
                Str_Get_Persone_Password = Edt_password.getText().toString().trim();
                Str_Get_Persone_Pincode = Edt_pincode.getText().toString().trim();

                Log.e(" Sign Up Fields data :", "\n"
                        + "Str_Get_Mtsn_Categoryname :" + "" + Str_Get_Mtsn_Categoryname + "\n"
                        + "Str_Get_Mtsn_StoreCategoryname :" + "" + Str_Get_Mtsn_StoreCategoryname + "\n"
                        + "Str_Get_Business_Name :" + "" + Str_Get_Business_Name + "\n"
                        + "Str_Get_Persone_Name :" + "" + Str_Get_Persone_Name + "\n"
                        + "Str_Get_Persone_Mobile :" + "" + Str_Get_Persone_Mobile + "\n"
                        + "Str_Get_Persone_Email :" + "" + Str_Get_Persone_Email + "\n"
                        + "Str_Get_Persone_Address :" + "" + Str_Get_Persone_Address + "\n"
                        + "Str_Get_Persone_Password :" + "" + Str_Get_Persone_Password + "\n"
                        + "Deviceid :" + "" + Deviceid + "\n"
                        + "Str_Get_Persone_Pincode :" + "" + Str_Get_Persone_Pincode + "\n");

                if (Str_Get_Mtsn_Categoryname.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Mtspn_store_type);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please Select Store Type", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Mtsn_Categoryname.equals("Please select Category")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Mtspn_store_type);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please Select Store Type", Toast.LENGTH_SHORT).show();

                } else if (Str_Get_Mtsn_StoreCategoryname.equalsIgnoreCase("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Mtspn_store_category);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Please select Store Type",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Mtsn_StoreCategoryname.equalsIgnoreCase("Please select Type")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Mtspn_store_category);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Please select Store Type",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Business_Name.equalsIgnoreCase("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_business_name);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Enter Business Name",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Persone_Name.equalsIgnoreCase("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_cp_name);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Enter Persone Name",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Persone_Mobile.equalsIgnoreCase("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_mobile);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Enter Mobile Number",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Persone_Email.equalsIgnoreCase("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_email);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Enter Valid Email ID",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Persone_Email.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_email);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Enter Valid Email ID", Toast.LENGTH_SHORT).show();


                } else if (!isValidEmaill(Str_Get_Persone_Email)) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    //	emailedit.requestFocus();
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_email);
                    /**************** End Animation ****************/
                    Toast.makeText(getApplicationContext(),
                            "Please enter valid email address.", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Persone_Address.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_address);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Enter Address", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_Persone_Password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_pincode);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Enter Password", Toast.LENGTH_SHORT).show();


                }else if (Str_Get_Persone_Password.length() < 5) {
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


                } else if (Str_Get_Persone_Password.contains(" ")) {
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


                } else if (Str_Get_Persone_Pincode.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_pincode);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Enter Pincode", Toast.LENGTH_SHORT).show();


                }
                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                    Log.e("OK Valid :", "OOOO");
                    Signup_Fast();


                }

            }
        });


        Mtspn_store_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                int posi = position;
                Str_Get_Mtsn_CategoryID = String.valueOf(posi);
                Str_Get_Mtsn_Categoryname = item;
                Log.e("posi ID:", "" + posi);
                Log.e("Str_Get_Mtsn_Categoryname:", "" + Str_Get_Mtsn_Categoryname);
                Log.e("Str_Get_Mtsn_CategoryID:", "" + Str_Get_Mtsn_CategoryID);

                Log.e("User_type Detail :", "Position :" + "" + position
                        + "\t" + "ID :" + "" + id
                        + "\t" + "Name :" + "" + item);

            }
        });
        Mtspn_store_type.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                android.support.design.widget.Snackbar.make(spinner, "Please Select Category",
                        android.support.design.widget.Snackbar.LENGTH_SHORT).show();

            }
        });

        Mtspn_store_category.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                int posi = position;
                Str_Get_Mtsn_StoreCategoryID = String.valueOf(posi);
                Str_Get_Mtsn_StoreCategoryname = item;
                Log.e("posi ID:", "" + posi);
                Log.e("Str_Get_Mtsn_StoreCategoryname:", "" + Str_Get_Mtsn_StoreCategoryname);
                Log.e("Str_Get_Mtsn_StoreCategoryID:", "" + Str_Get_Mtsn_StoreCategoryID);

                Log.e("User_type Detail :", "Position :" + "" + position
                        + "\t" + "ID :" + "" + id
                        + "\t" + "Name :" + "" + item);

            }
        });
        Mtspn_store_category.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                android.support.design.widget.Snackbar.make(spinner, "Please Select Category",
                        android.support.design.widget.Snackbar.LENGTH_SHORT).show();

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
                City_typelist.clear();

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


    private void Signup_Fast() {

        Log.e("Signup********************* :", "*************************");

        pDialog.setMessage("Wait ...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + SIGNUP)
                .addBodyParameter("Name", Str_Get_Persone_Name)
                .addBodyParameter("BusinessName", Str_Get_Business_Name)
                .addBodyParameter("Mobile", Str_Get_Persone_Mobile)
                .addBodyParameter("Email", Str_Get_Persone_Email)
                .addBodyParameter("Password", Str_Get_Persone_Password)
                .addBodyParameter("Address", Str_Get_Persone_Address)
                .addBodyParameter("CityId", Str_Get_Mtsn_CategoryID)
                .addBodyParameter("Pincode", Str_Get_Persone_Pincode)
                .addBodyParameter("Longitude", "88888")
                .addBodyParameter("Latitude", "88888")
                .addBodyParameter("DeviceId", Deviceid)
                .addBodyParameter("StoreCategory", Str_Get_Mtsn_StoreCategoryID)
                .addBodyParameter("Category", "1")
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
                            StoreCategory_typelist.add("Please select Category");
                            JSONObject jobjresponse = new JSONObject(String.valueOf(responsee));
                            Log.e("jobjresponse List:", "" + jobjresponse);

                            JSONObject jobjControl = jobjresponse.getJSONObject("Control");
                            Str_Get_Control_Status = jobjControl.getString("Status");
                            Str_Get_Control_Message = jobjControl.getString("Message");


                            if (Str_Get_Control_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_Control_Status is:", "1");

                                Str_Get_Control_Message = jobjControl.getString("Message");
                                Log.e("Str_Get_Control_Message :", "" + Str_Get_Control_Message);


                                Log.e("SIgnup :", "GOOD");

                                Toast.makeText(SignupActivity.this, "Signup Complete", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e("SIgnup :", "ERROR");
                                Toast.makeText(SignupActivity.this, "Signup Error", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SignupActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        Log.e("Error Response :", "" + error.toString());
                    }
                });
    }


    private void Get_Store_List_Fast() {

        Log.e("GET_STORE_LIST_DETAIL********************* :", "*************************");

        pDialog.setMessage("Fetching ...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + GET_STORE_LIST_DETAIL)
                .addBodyParameter("CategoryLevel", "1")
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
                            StoreCategory_typelist.add("Please select Category");
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
                                    Str_Get_StoreCategoryName = jsonObject.getString("CategoryName");
                                    Str_Get_StoreCategoryID = jsonObject.getString("CategoryId");
                                    Str_Get_CategoryIcon = jsonObject.getString("CategoryIcon");
                                    StoreCategory_typelist.add(Str_Get_StoreCategoryName);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item, StoreCategory_typelist);
                                    Mtspn_store_type
                                            .setItems(StoreCategory_typelist);
                                    Str_Get_Category_list_default = StoreCategory_typelist.get(0);
                                    Log.e("Str_Get_Category_list_default ", "" + Str_Get_Category_list_default);

                                    Log.e(" Category list result :", "" + StoreCategory_typelist.size());
                                    Log.e("All Category list Names:", "" + StoreCategory_typelist
                                            + "\n" + "item 0 :" + "" + StoreCategory_typelist.size());

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


    private void Get_Store_Category_List_Fast() {

        Log.e("Get_Store_Category_List_Fast********************* :", "*************************");

        pDialog.setMessage("Fetching ...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + GET_STORE_CATEGORY_LIST_DETAIL)
                .setTag("StorelCategoryist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responsee) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response :", "" + responsee);

                        try {
                            Category_typelist.add("Please select Type");
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
                                    Str_Get_CategoryName = jsonObject.getString("CategoryName");
                                    Str_Get_CategoryID = jsonObject.getString("CategoryTypeId");

                                    Category_typelist.add(Str_Get_CategoryName);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item, Category_typelist);
                                    Mtspn_store_category.setItems(Category_typelist);
                                    Str_Get_StoreCategory_list_default = Category_typelist.get(0);
                                    Log.e("Str_Get_StoreCategory_list_default ", "" + Str_Get_StoreCategory_list_default);

                                    Log.e(" Category list result :", "" + Category_typelist.size());
                                    Log.e("All Category list Names:", "" + Category_typelist
                                            + "\n" + "item 0 :" + "" + Category_typelist.size());

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
