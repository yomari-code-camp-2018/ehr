package com.yala.sushant.ehr_backend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrescriptionSingleActivity extends AppCompatActivity {

    TextView date, dr, prob, rec, phycExam, Medication;


    String recId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_single);
        recId = getIntent().getStringExtra("userId");

        initUI();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Prescription").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(recId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dateS=dataSnapshot.child("time").getValue().toString();
                UtilClass ut=new UtilClass();
                date.setText(ut.getTime(Long.parseLong(dateS)));
                dr.setText(dataSnapshot.child("doctorId").getValue().toString());
                prob.setText(dataSnapshot.child("patientComplain").getValue().toString());
                rec.setText(dataSnapshot.child("recommendation").getValue().toString());
                phycExam.setText(dataSnapshot.child("physicalExamination").getValue().toString());
                Medication.setText(dataSnapshot.child("medication").getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void initUI() {
        date = (TextView) findViewById(R.id.txtDate);
        dr = (TextView) findViewById(R.id.txtDrName);
        prob = (TextView) findViewById(R.id.txtProblem);
        rec = (TextView) findViewById(R.id.txtRecomendation);
        phycExam = (TextView) findViewById(R.id.txtPhysicalExamination);
        Medication = (TextView) findViewById(R.id.txtMedication);
    }
}
