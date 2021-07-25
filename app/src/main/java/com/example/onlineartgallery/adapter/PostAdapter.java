package com.example.onlineartgallery.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineartgallery.Model.User;
import com.example.onlineartgallery.Post;
import com.example.onlineartgallery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Constructor;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;

    public  PostAdapter(Context mContext,List<Post> mPost){
        this.mContext=mContext;
        this.mPost= mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,parent,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
       Post post= mPost.get(position);

      Glide.with(mContext).load(post.getPostImage()).into(holder.post_image);
       // Glide.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/online-art-gallery-pr.appspot.com/o/posts%2F1621946848496.null?alt=media&token=27e8d559-95aa-4042-af71-d3d2c1b926e8").into(holder.post_image);

       if(post.getTitle().equals("")){
           holder.title.setVisibility(View.GONE);

       }else{
           holder.title.setVisibility(View.VISIBLE);
           holder.title.setText(post.getTitle());
       }

        if(post.getMedium().equals("")){
            holder.medium.setVisibility(View.GONE);

        }else{
            holder.medium .setVisibility(View.VISIBLE);
            holder.medium.setText(post.getMedium());
        }

        if(post.getDimensions().equals("")){
            holder.dimensions.setVisibility(View.GONE);

        }else{
            holder.dimensions.setVisibility(View.VISIBLE);
            holder.dimensions.setText(post.getDimensions());
        }

        if(post.getPrice().equals("")){
            holder.price.setVisibility(View.GONE);

        }else{
            holder.price.setVisibility(View.VISIBLE);
            holder.price.setText(post.getPrice());
        }





       publisherInfo(holder.image_profile,holder.username,holder.publisher,post.getPublisher());

    }

    @Override
    public int getItemCount()
    {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile,post_image,like,comment,save;
        public TextView username ,likes,publisher,title,comments,medium,price,dimensions;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
           // comments = itemView.findViewById(R.id.comments);
            //like = itemView.findViewById(R.id.like);
           // comment = itemView.findViewById(R.id.comment);
            //save= itemView.findViewById(R.id.save);
            username= itemView.findViewById(R.id.username);
           // likes = itemView.findViewById(R.id.likes);
           // publisher = itemView.findViewById(R.id.publisher);
            title = itemView.findViewById(R.id.title);
            medium = itemView.findViewById(R.id.medium);
            price = itemView.findViewById(R.id.price);
            dimensions = itemView.findViewById(R.id.dimensions);
        }
    }

    private void publisherInfo(ImageView image_profile,TextView username, TextView publisher,String userid){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                com.example.onlineartgallery.Model.User user = snapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageUrl()).into(image_profile);
                username.setText(user.getUsername());
               // publisher.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
