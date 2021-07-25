package com.example.onlineartgallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    Uri imageUri;
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close, image_added;
    TextView post;
    EditText title,medium,price,dimensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        close = findViewById(R.id.close);
        image_added= findViewById(R.id.image_added);
        post= findViewById(R.id.post);
        title = findViewById(R.id.title);
        medium = findViewById(R.id.medium);
        price = findViewById(R.id.price);
        dimensions = findViewById(R.id.dimensions);

        storageReference = FirebaseStorage.getInstance().getReference("posts");


        close.setOnClickListener(v -> {
            startActivity(new Intent(PostActivity.this,home2.class));
            finish();
        });

        post.setOnClickListener(v -> uploadImage());

        CropImage.activity().setAspectRatio(1,1).start(PostActivity.this);


    }






    private  String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    private  void uploadImage(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();
        if(imageUri!=null){
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask((Continuation) task -> {

                if (!task.isComplete())
                    throw task.getException();

                return fileReference.getDownloadUrl();

            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = (Uri) task.getResult();
                    myUri = downloadUri.toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                    String postId = reference.push().getKey();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("postId", postId);
                    hashMap.put("postImage", myUri);
                    hashMap.put("title", title.getText().toString().trim());
                    hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    hashMap.put("medium", medium.getText().toString().trim());
                    hashMap.put("price", price.getText().toString().trim());
                    hashMap.put("dimensions", dimensions.getText().toString());



                    reference.child(postId).setValue(hashMap);

                    progressDialog.dismiss();
                    //new Post(postId, myUri,title.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(),  medium.getText().toString(), price.getText().toString(), dimensions.getText().toString());

                    startActivity(new Intent(PostActivity.this, home2.class));
                    finish();

                } else {
                    Toast.makeText(PostActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }).addOnFailureListener((OnFailureListener) e -> Toast.makeText(PostActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show());

        }
        else {

                Toast.makeText(this,"No Image Selected",Toast.LENGTH_SHORT).show();

            }
        }







    //ctrl+o


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            image_added.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "something gone wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this, home2.class));
            finish();

        }

    }

}
