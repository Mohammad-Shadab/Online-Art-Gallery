package com.example.onlineartgallery;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class editBio extends AppCompatActivity {

    TextView bio, skills;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;//,bioSkill ;
    Button saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);

        bio= findViewById(R.id.bioEdit);
        skills = findViewById(R.id.skillsEdit);
        saveButton = findViewById(R.id.saveButton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
       // bioSkill = fAuth.getCurrentUser().getUid();

        //DocumentReference documentReference = fStore.collection("users").document(userID); //correct this
          DocumentReference documentReference = fStore.collection("users").document(userID).collection("bioSkills").document("profile");

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

              bio.setText(value.getString("bio"));
              skills.setText(value.getString("skills"));


            }
        });



        saveButton.setOnClickListener(v -> {

            String bioString = bio.getText().toString();
            String skillsString = skills.getText().toString();

            Map<String,Object> user = new HashMap<>();
            user.put("bio",bioString);
            user.put("skills",skillsString);

           // user.put("fName",name);
           // user.put("email",email);
           // user.put("phone",phone); //search how to add fields in a document

           //DocumentReference documentReference = fStore.collection("users").document(userID);
            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(editBio.this,"Changes Saved.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),profile.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("failhogya","on failure"+ e.getMessage());
                }
            });



        });

    }


}