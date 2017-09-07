package com.merchantapp.ritesh;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.merchantapp.ritesh.ucropImagepicker.PickerBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ritesh on 6/9/17.
 */

public class ImageUpload extends AppCompatActivity {


    private ProgressDialog pDialog;
    Dialog QuickTipDialog;

    @BindView(R.id.tv_btn_upload)
    TextView Tv_btn_upload;

    @BindView(R.id.iv_upload_image)
    ImageView Iv_upload_image;

    @BindView(R.id.rl_upload_btn)
    RelativeLayout Rl_upload_btn;


    String Str_profileImage_path = "", Sh_pre_User_ID = "", Str_Get_Control_Status = "", Str_Get_Control_Message = "";

    private int PICK_IMAGE_REQUEST = 1;

    private static final String UPDATE_IMAGE = "Store/uploadImage";

    File file;
    Uri uri;
    Intent CamIntent, GalIntent, CropIntent;
    public static final int RequestPermissionCode = 1;
    DisplayMetrics displayMetrics;
    int width, height;

    File ProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageupload);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);

        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        Tv_btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Str_profileImage_path.equalsIgnoreCase("")) {


//                    Update_Image_Fast();
                    Update_Image_volley();
                } else {
                    Toast.makeText(ImageUpload.this, "Please select image", Toast.LENGTH_LONG).show();

                }
            }
        });

        Rl_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProfileImagePicker();

                /*Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/

//                GetImageFromGallery();
//                ProfileImagePicker();


                new PickerBuilder(ImageUpload.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_pan_card.setImageURI(imageUri);
                                Glide.with(Iv_upload_image.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_upload_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_upload_image
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_upload_image);
                                            }
                                        });


                                Log.e("Iv_pan_card imageUri path SELECT_FROM_GALLERY :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_profileImage_path = imageUri.getPath();
                                Log.e("Str_Pan_Card_path SELECT_FROM_GALLERY :", "" + Str_profileImage_path);
                                if (!Str_profileImage_path.equals("")) {

                                    Log.e("Pan_Card_Image File Image availabe :", "" + ProfileImage);
                                    ProfileImage = new File(Str_profileImage_path);
                                    Log.e("Pan_Card_Image File Image:", "" + Str_profileImage_path);
                                    Iv_upload_image.setVisibility(View.VISIBLE);
//                                    UpdateMerchant_Pan_Card_Image_Fast();

                                } else {

                                    Log.e("Pan_Card_Image File Image blank:", "" + ProfileImage);
                                    ProfileImage = new File("Pan_Card_Image");
                                    Log.e("Pan_Card_Image File Image:", "" + ProfileImage);
                                    Iv_upload_image.setVisibility(View.GONE);

                                }

                            }
                        })
                        .setImageName("ImageID")
                        .setImageFolderName("BusinessApp")
                        .setCropScreenColor(Color.CYAN)
                        .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                            @Override
                            public void onPermissionRefused() {

                            }
                        })
                        .start();

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

            ImageCropFunction();

        } else if (requestCode == 2) {

            if (data != null) {

                uri = data.getData();

                ImageCropFunction();

            }
        } else if (requestCode == 1) {

            if (data != null) {

                Bundle bundle = data.getExtras();

                Bitmap bitmap = bundle.getParcelable("data");

                Iv_upload_image.setImageBitmap(bitmap);

            }
        }
    }


    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }


    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ImageUpload.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ImageUpload.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void Update_Image_volley() {
        Log.e("Update_Image_volley********************* :", "*************************");


        Log.e("Fast Update_Image_volley Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();
        //Auth header
        Map<String, String> mHeaderPart = new HashMap<>();
        mHeaderPart.put("Content-type", "multipart/form-data;");

        //File part
        Map<String, File> mFilePartData = new HashMap<>();
        mFilePartData.put("UserImage", new File(Str_profileImage_path));

        //String part
        Map<String, String> mStringPart = new HashMap<>();
        mStringPart.put("MerchantId", Sh_pre_User_ID);


        CustomMultipartRequest mCustomRequest = new CustomMultipartRequest(Request.Method.POST,
                ImageUpload.this, HttpUrlPath.urlPathMain + UPDATE_IMAGE,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        hideDialog();
                        Log.e("jsonObject Response :", "" + jsonObject.toString());
                        try {
                            JSONObject jobjresponse = new JSONObject(jsonObject.toString());
                            Log.e("jobjresponse List:", "" + jobjresponse);

                            JSONObject jobjControl = jobjresponse.getJSONObject("Control");
                            Str_Get_Control_Status = jobjControl.getString("Status");
                            Str_Get_Control_Message = jobjControl.getString("Message");


                            if (Str_Get_Control_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_Control_Status is:", "1");

                                Str_Get_Control_Message = jobjControl.getString("Message");
                                Log.e("Str_Get_Control_Message :", "" + Str_Get_Control_Message);

                                JSONObject jobjData = jobjresponse.getJSONObject("Data");
//                                Str_MerchantID = jobjData.getString("MerchantId");
//                                Log.e("Str_MerchantID :", "" + Str_MerchantID);
                                Log.e("SIgnup :", "GOOD");

                                Log.e("Value save in Sharedpre", "ok");

                                Appconstant.editor.putString("imageuploadOk", "ImageUploadTrue");
                                Appconstant.editor.putString("loginTest", "true");
                                Appconstant.editor.commit();
                                Toast.makeText(ImageUpload.this, "Image Upload Completed", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(SignupActivity.this, "Your ID is :" + Str_MerchantID.toString(), Toast.LENGTH_SHORT).show();
                                Intent Gologincreen = new Intent(getApplicationContext(), DashBoard.class);
                                startActivity(Gologincreen);
                                finish();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                listener.onErrorResponse(volleyError);
                hideDialog();
                Log.e("Response volleyError:", "" + volleyError);

            }
        }, mFilePartData, mStringPart, mHeaderPart);


        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Adding request to the queue
        mCustomRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(mCustomRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
