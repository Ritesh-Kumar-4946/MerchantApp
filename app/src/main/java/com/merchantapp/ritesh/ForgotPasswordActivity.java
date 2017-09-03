package com.merchantapp.ritesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ritesh on 2/9/17.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Intent i = new Intent(ForgotPasswordActivity.this, VarifyMobileActivity.class);
        startActivity(i);
        finish();
    }
}
