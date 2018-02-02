package com.yala.sushant.ehr_backend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReportFeedActivity extends AppCompatActivity {
    String feedUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_feed);
        feedUserId = getIntent().getStringExtra("FeedUserId");

    }
}
