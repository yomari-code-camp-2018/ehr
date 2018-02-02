package com.yala.sushant.ehr_backend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MakePrescriptionActivity extends AppCompatActivity {

    TextView txt_UserName, txt_DigitalId;
    EditText edt_PatientComplain, edt_PhysicalExamination, edt_Recomendation,edt_Medication;
    Button btn_UploadPresciption;
    private ProgressDialog progDialog;
    String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_prescription);

        initUI();
        progDialog = new ProgressDialog(this);

        //getintent and fix patientId
        patientId = getIntent().getStringExtra("FeedUserId");


        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference().child("Patient").child(patientId);
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                txt_UserName.setText("Name: " + dataSnapshot.child("fullname").getValue().toString());

                txt_DigitalId.setText("Id: " + dataSnapshot.child("digitalId").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_UploadPresciption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPresciption();
            }
        });

    }


    private void initUI() {
        txt_UserName = (TextView) findViewById(R.id.txt_UserName);

        edt_PatientComplain = (EditText) findViewById(R.id.edt_PatientComplain);

        edt_PhysicalExamination = (EditText) findViewById(R.id.edt_PhysicalExamination);

        edt_Recomendation = (EditText) findViewById(R.id.edt_Recomendation);

        btn_UploadPresciption = (Button) findViewById(R.id.btn_UploadPresciption);

        txt_DigitalId = (TextView) findViewById(R.id.txt_DigitalId);
        edt_Medication= (EditText) findViewById(R.id.edt_Medication);
    }


    private void uploadPresciption() {

        String patientComplain, physicalExamination, recommendation,medication;
        patientComplain = edt_PatientComplain.getText().toString().trim();
        physicalExamination = edt_PhysicalExamination.getText().toString().trim();
        recommendation = edt_Recomendation.getText().toString().trim();
        medication=edt_Medication.getText().toString().trim();
        if(TextUtils.isEmpty(medication)){
            medication="none";
        }

        progDialog.setMessage("Uploading");
        progDialog.show();
        DatabaseReference patientPresciption = FirebaseDatabase.getInstance().getReference().child("Prescription").child(patientId);
        String pushId = patientPresciption.push().getKey();


        final Map patientPresciptionMap = new HashMap();
        patientPresciptionMap.put("patientComplain", patientComplain);
        patientPresciptionMap.put("physicalExamination", physicalExamination);
        patientPresciptionMap.put("doctorId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        patientPresciptionMap.put("recommendation", recommendation);
        patientPresciptionMap.put("medication", medication);
        patientPresciptionMap.put("time", ServerValue.TIMESTAMP);
        patientPresciption.child(pushId).setValue(patientPresciptionMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progDialog.dismiss();
                Intent startIntent = new Intent(MakePrescriptionActivity.this, DoctorHomeActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startIntent);
            }
        });


    }
}
