package com.example.onlineartgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineartgallery.Post;
import com.example.onlineartgallery.R;

import java.util.List;

public class MyFotosAdapter extends  RecyclerView.Adapter<MyFotosAdapter.ViewHolder>{

    private Context context;
    private List<Post> mPosts;


    public  MyFotosAdapter(Context context,List<Post> mPosts){
        this.context=context;
        this.mPosts=mPosts;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.fotos_item,parent,false);
        return new MyFotosAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = mPosts.get(position);
        Glide.with(context).load(post.getPostImage()).into(holder.post_image2);

    }

    @Override
    public int getItemCount()
    {
        return mPosts.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView post_image2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_image2 = itemView.findViewById(R.id.post_image_fotos);


        }
    }
}
