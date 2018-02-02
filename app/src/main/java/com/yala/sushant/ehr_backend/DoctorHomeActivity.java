package com.yala.sushant.ehr_backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoctorHomeActivity extends AppCompatActivity {

    TextView txt_fullname;
    Button testBtn, btn_ViewRecord, btn_ViewReport;
    String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        patientId="TfOmmRLvOicMKGcpXv7eqeBj30g1";

        testBtn = (Button) findViewById(R.id.btn_test);
        btn_ViewRecord = (Button) findViewById(R.id.btn_ViewRecord);
        btn_ViewReport = (Button) findViewById(R.id.btn_ViewReport);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(DoctorHomeActivity.this, MakePrescriptionActivity.class);
                startIntent.putExtra("FeedUserId", patientId);
                startActivity(startIntent);
            }
        });

        btn_ViewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(DoctorHomeActivity.this, PrescriptionFeedActivity.class);
                startIntent.putExtra("FeedUserId", patientId);
                startActivity(startIntent);
            }
        });

        btn_ViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(DoctorHomeActivity.this, ReportFeedActivity.class);
                startIntent.putExtra("FeedUserId", patientId);
                startActivity(startIntent);
            }
        });

    }
}
