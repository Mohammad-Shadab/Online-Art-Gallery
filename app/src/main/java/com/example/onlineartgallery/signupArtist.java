package com.example.onlineartgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineartgallery.Model.User;
import com.example.onlineartgallery.ui.login.loginArtist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupArtist extends AppCompatActivity {

    public void login(View view)

    {
        Intent intent = new Intent(signupArtist.this, loginArtist.class);

        startActivity(intent);
        finish();

    }



    EditText Name, Email, Phone, Password ,Password2,Username;
    Button SignUp;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseFirestore fStore ;

    ProgressDialog pd;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_artist);

        Name= findViewById(R.id.name);
        Email= findViewById(R.id.email);
        Phone= findViewById(R.id.phone);
        Password=findViewById(R.id.createPasssword);
        Password2 =findViewById(R.id.confirmPassword);
        SignUp = findViewById(R.id.signUp);
        Username = findViewById(R.id.username_register);

        fAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser()!=null){
           startActivity(new Intent(signupArtist.this, home2.class));
            //FirebaseAuth.getInstance().signOut();
            finish();
        }

        SignUp.setOnClickListener(v -> {

           pd= new ProgressDialog(signupArtist.this);
           pd.setMessage("Please wait..");
           pd.show();

            String email= Email.getText().toString().trim();
            String password = Password.getText().toString().trim();
            String password2 = Password2.getText().toString().trim();
            String name= Name.getText().toString();
            String phone= Phone.getText().toString().trim();
            String username= Username.getText().toString().trim();
            if(TextUtils.isEmpty(name)) {
                Name.setError("Name is Required.");
                return;

            }

            else if (TextUtils.isEmpty(email)) {
                Email.setError("Email is Required.");
                return;

            }

            else if (TextUtils.isEmpty(username)) {
                Username.setError("Username is Required.");
                return;

            }


            else if(TextUtils.isEmpty(phone)) {
                Phone.setError("Mobile number is Required.");
                return;

            }

            else if(TextUtils.isEmpty(password)) {

                Password.setError("Password is Required.");
                return;
            }



            else if(password.length()<6)
            {
                Password.setError("Password must be longer than 5 characters");
                return;

            }

            else if(!password.equals(password2))
            {
                Password2.setError("Passwords do not match!");
                return;

            }

            else {

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Toast.makeText(signupArtist.this,"User Created",Toast.LENGTH_SHORT).show();

                        FirebaseUser firebaseUser = fAuth.getCurrentUser();
                        userID = firebaseUser.getUid();
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                        // DocumentReference documentReference  = fStore.collection("users").document(userID);
                        HashMap<String, Object> user = new HashMap<>();

                        user.put("id", userID);
                        user.put("username", username.toLowerCase());
                        user.put("fullname", name);
                        user.put("email", email);
                        user.put("phone", phone);
                        user.put("bio", "");
                        user.put("skills", "");
                        user.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/online-art-gallery-pr.appspot.com/o/profile_placeholder.png?alt=media&token=9ba4f9c0-ba20-4ce2-ba40-56c92664ef4c");

                        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    Intent intent = new Intent(signupArtist.this, home2.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });



                               /* documentReference.set(user).addOnSuccessListener(aVoid -> Log.d("successhogya","onSuccess: user profile created" + userID)).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("failhogya","on failure"+ e.getMessage());
                                    }
                                });

                                */


                        //startActivity(new Intent(getApplicationContext(),home2.class));
                    } else {
                        pd.dismiss();
                        Toast.makeText(signupArtist.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            }


        });

    }
}