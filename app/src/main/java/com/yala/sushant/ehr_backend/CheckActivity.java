package com.yala.sushant.ehr_backend;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            checkUserType(userId);
        } else {
            sendToStart();
        }
    }


    private void sendToStart() {
        Intent startIntent = new Intent(CheckActivity.this, LoginActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startIntent);
    }

    private void checkUserType(final String userId) {

        DatabaseReference doctorRef = FirebaseDatabase.getInstance().getReference().child("Doctor").child(userId);
        doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(CheckActivity.this, "Signup as doctor or patient.", Toast.LENGTH_SHORT).show();
                    showSignupDialog();
                } else {
                    checkForUser(userId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void checkForUser(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Patient").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Intent startIntent = new Intent(CheckActivity.this, UserHomeActivity.class);
                    startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startIntent);
                } else {
                    sendToStart();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void showSignupDialog() {
        AlertDialog.Builder diaglogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.signup_dialog, null);
        diaglogBuilder.setView(dialogView);

        final Button buttonSignupDoctor = (Button) dialogView.findViewById(R.id.buttonSignupDoctor);

        final Button buttonSignupUser = (Button) dialogView.findViewById(R.id.buttonSignupUser);

        final AlertDialog alertDialog = diaglogBuilder.create();
        alertDialog.show();

        buttonSignupDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(CheckActivity.this, DoctorHomeActivity.class);
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(startIntent);
            }
        });

        buttonSignupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(CheckActivity.this, UserHomeActivity.class);
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(startIntent);
            }
        });

    }
}
