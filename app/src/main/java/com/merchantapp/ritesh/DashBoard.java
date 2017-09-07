package com.merchantapp.ritesh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ritesh on 8/9/17.
 */

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
