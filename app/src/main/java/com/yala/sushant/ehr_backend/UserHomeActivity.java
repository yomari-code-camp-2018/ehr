package com.yala.sushant.ehr_backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserHomeActivity extends AppCompatActivity {

    Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        test=(Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(UserHomeActivity.this, PrescriptionFeedActivity.class);
                mainIntent.putExtra("FeedUserId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(mainIntent);
            }
        });
    }
}
