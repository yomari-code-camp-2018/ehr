package com.yala.sushant.ehr_backend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yala.sushant.ehr_backend.model.Patient;

public class SignupActivity extends AppCompatActivity {


    //UI Stuff
    EditText edt_fullName, edt_phone, edt_email, edt_digitalId, edt_password;
    EditText edt_type;
    Button btn_signup;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog progDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();

        mAuth = FirebaseAuth.getInstance();
        progDialog = new ProgressDialog(this);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type;
                String fullName, phone, email, digitalId, password;
                fullName = edt_fullName.getText().toString().trim();
                phone = edt_phone.getText().toString().trim();
                digitalId = edt_digitalId.getText().toString().trim();
                email = edt_email.getText().toString().trim();
                password = edt_password.getText().toString().trim();
                Patient patient = new Patient(email, fullName, digitalId, phone);
                startSignupPatient(patient, password);
            }
        });
    }

    private void initUI() {
        edt_fullName = (EditText) findViewById(R.id.edt_fullName);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_digitalId = (EditText) findViewById(R.id.edt_digitalId);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_signup = (Button) findViewById(R.id.btn_signup);
//        edt_type = (EditText) findViewById(R.id.edt_type);
    }


    private void startSignupPatient(final Patient signupPatient, String password) {

        Toast.makeText(this, "Before " + signupPatient.getEmail(), Toast.LENGTH_SHORT).show();


        if (!TextUtils.isEmpty(signupPatient.getFullname()) && !TextUtils.isEmpty(signupPatient.getEmail()) && !TextUtils.isEmpty(password)) {
            Toast.makeText(this, password + " After" + signupPatient.getDigitalId(), Toast.LENGTH_SHORT).show();
            progDialog.setMessage("Signing UP");
            progDialog.show();

            mAuth.createUserWithEmailAndPassword(signupPatient.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        String userId = mAuth.getCurrentUser().getUid();

                        DatabaseReference patientRef = FirebaseDatabase.getInstance().getReference().child("Patient");

                        //User=> UserID
                        DatabaseReference currentUserDb = patientRef.child(userId);
                        currentUserDb.child("fullname").setValue(signupPatient.getFullname());
                        currentUserDb.child("digitalId").setValue(signupPatient.getDigitalId());
                        currentUserDb.child("email").setValue(signupPatient.getEmail());
                        currentUserDb.child("phone").setValue(signupPatient.getPhoneNo());

                        progDialog.dismiss();



                        Intent startIntent = new Intent(SignupActivity.this, LoginActivity.class);
                        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(startIntent);



                        /*
                            Intent mainIntent = new Intent(SignupDoctorActivity.this, ArticleFeedActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                        */
                    }


                }
            });

        }


    }
}
