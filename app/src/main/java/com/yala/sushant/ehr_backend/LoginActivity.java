package com.yala.sushant.ehr_backend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText edt_email, edt_password;
    TextView txt_signup;
    Button btn_login;
    String userID;
    private ProgressDialog mProgress;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();


        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        //Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkLogin();

            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupDialog();
            }
        });


    }

    private void initUI() {


        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_signup = (TextView) findViewById(R.id.txt_signup);

    }


    private void checkLogin() {

        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgress.setMessage("logging in...");
            mProgress.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mProgress.dismiss();
                        userID = mAuth.getCurrentUser().getUid();
                        Intent mainIntent = new Intent(LoginActivity.this, CheckActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);

                    } else {
                        mProgress.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Error.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


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

                startActivity(new Intent(LoginActivity.this, SignupDoctorActivity.class));

            }
        });


        buttonSignupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }


}
