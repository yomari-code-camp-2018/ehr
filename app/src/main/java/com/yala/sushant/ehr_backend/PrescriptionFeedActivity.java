package com.yala.sushant.ehr_backend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yala.sushant.ehr_backend.model.Article;
import com.yala.sushant.ehr_backend.model.Presciption;


public class PrescriptionFeedActivity extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;

    String feedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_feed);

        feedUserId = getIntent().getStringExtra("FeedUserId");


        //RecycleView+> Blog row
        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
//        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("Prescription").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Prescription").child(feedUserId);

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Presciption, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Presciption, BlogViewHolder>(
                Presciption.class,
                R.layout.pres_row,
                BlogViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Presciption model, int position) {
                //Returns URL
                final String userId = getRef(position).getKey();

                viewHolder.setProblem(model.getPatientComplain());
                String setBody= model.getPhysicalExamination().substring(0, Math.min(model.getPhysicalExamination().length(),200))+"...";
                viewHolder.setPhysicalExamination(setBody);
                UtilClass utilClass= new UtilClass();
                viewHolder.setTime("Time : "+utilClass.getTime(model.getTime()));
//                viewHolder.setDoctorName("Dr. "+model.getDoctorId());

                setDoctorName(viewHolder,model.getDoctorId());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent singleBlogIntent = new Intent(PrescriptionFeedActivity.this, PrescriptionSingleActivity.class);
                        singleBlogIntent.putExtra("userId", userId);
                        startActivity(singleBlogIntent);
                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }



    private void setDoctorName(final BlogViewHolder viewHolder, String userId){

        DatabaseReference dr=FirebaseDatabase.getInstance().getReference().child("Doctor").child(userId);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    viewHolder.setDoctorName("Dr. "+dataSnapshot.child("fullname").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    //ViewHolder
    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setProblem(String title) {

            TextView problem = (TextView) mView.findViewById(R.id.problem);
            problem.setText(title);

        }

        public void setPhysicalExamination(String body) {
            TextView physicalExamination = (TextView) mView.findViewById(R.id.physicalExamination);
            physicalExamination.setText(body);
        }

        public void setTime(String username) {
            TextView post_username = (TextView) mView.findViewById(R.id.txtTime);
            post_username.setText(username);
        }

        public void setDoctorName(String doctorName) {
            TextView post_doctorName = (TextView) mView.findViewById(R.id.txtDoctor);
            post_doctorName.setText(doctorName);
        }

    }

}
