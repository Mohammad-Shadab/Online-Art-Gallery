package com.example.onlineartgallery.ui.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineartgallery.R;
import com.example.onlineartgallery.home;
import com.example.onlineartgallery.home2;
import com.example.onlineartgallery.signupArtist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class loginArtist extends AppCompatActivity {



    public void signUpArtist(View view)

    {
        Intent intent = new Intent(loginArtist.this, signupArtist.class);

        startActivity(intent);

    }


    public void forgotPassword (View view)
    {
        EditText resetMail = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password ?");
        passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    String mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(loginArtist.this,"Reset Link Sent To Your Email.",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(loginArtist.this,"Error! Reset Link Is Not Sent."+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });


        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        passwordResetDialog.create().show();
    }




    EditText Email,  Password ;
    Button SignIn;
    FirebaseAuth fAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_artist);

        Email = findViewById(R.id.uname);
        Password = findViewById(R.id.passwd);

        SignIn = findViewById(R.id.LOGIN);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(loginArtist.this, home2.class));
            //FirebaseAuth.getInstance().signOut();
            finish();
        }

        SignIn.setOnClickListener(v -> {

           // Log.i("this","login working");

            ProgressDialog pd = new ProgressDialog(loginArtist.this);
            pd.setMessage("Please wait..");
            pd.show();

            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Email.setError("Email is Required.");
                return;

            }


            else if (TextUtils.isEmpty(password)) {

                Password.setError("Password is Required.");
                return;
            }

            else if (password.length() < 6) {
                Password.setError("Password must be longer than 5 characters");
                return;

            }
            else {
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(fAuth.getCurrentUser().getUid());

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                pd.dismiss();
                                Intent intent = new Intent(loginArtist.this,home2.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                    pd.dismiss();
                            }
                        });

                       // Toast.makeText(loginArtist.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(getApplicationContext(), home2.class));
                    } else {
                        pd.dismiss();
                        Toast.makeText(loginArtist.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}












