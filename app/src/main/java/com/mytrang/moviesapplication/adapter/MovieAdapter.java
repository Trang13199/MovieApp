package com.mytrang.moviesapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytrang.moviesapplication.DescriptionActivity;
import com.mytrang.moviesapplication.LoginMainActivity;
import com.mytrang.moviesapplication.R;
import com.mytrang.moviesapplication.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
   private Context context;
   private List<Movies> arrayList = new ArrayList<>();
   private MovieItemListener mItemListener;

   SharedPreferences preferences;

    public MovieAdapter(Context context, List<Movies> arrayList, MovieItemListener movieItemListener) {
        this.context = context;
        this.arrayList = arrayList;
        mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.custom_list,parent , false);
        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movies movies = arrayList.get(position);

        String name = movies.getTitle();
        if(name.contains("/")){
            String[] header = name.split("/");
            for (int i = 0 ; i<header.length;i++){
                holder.title.setText(header[0]);
                holder.name.setText(header[i]);
            }
        }else{
            holder.title.setText(movies.getTitle());
            holder.name.setText(null);
        }


        String comment = movies.getDescription();
        if(comment.length()>200){
            holder.content.setText(comment.substring(0, 200)+"...");
        }else {
            holder.content.setText(comment);
        }

        Picasso.get()
                .load(movies.getImage())
//                .placeholder(R.drawable.ic_bg)
                .into(holder.imageView);
    }

    public void updateAnswer(List<Movies> list){
        arrayList = list;
        notifyDataSetChanged();
    }
    public Movies getMovie(int adapterPosition){
        return arrayList.get(adapterPosition);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView, imgLike;
        TextView title, name, views, content, txtLike;
        MovieItemListener mmovieItemListener;
        Button watch;

        public ViewHolder(@NonNull View itemView, MovieItemListener movieItemListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.txt_title);
            name = itemView.findViewById(R.id.txt_titlevn);
            views = itemView.findViewById(R.id.txt_view);
            content = itemView.findViewById(R.id.txt_content);

            imgLike = itemView.findViewById(R.id.img_like);
            txtLike = itemView.findViewById(R.id.txt_like);

            imgLike.setOnClickListener(this);


            this.mmovieItemListener = movieItemListener;
            watch = itemView.findViewById(R.id.btn_xemthem);
            watch.setOnClickListener(this);

//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_xemthem:
                    watchVideo(view);
                    break;
                case R.id.img_like:
                    setImage();
                    break;
            }
        }

        private void watchVideo(View view) {
            preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
            if (preferences.getString("email", "").isEmpty() && preferences.getString("password", "").isEmpty()) {
                Intent login = new Intent(view.getContext(), LoginMainActivity.class);
                view.getContext().startActivity(login);
            } else {
                Movies movies = getMovie(getAdapterPosition());
                notifyDataSetChanged();

                Intent intent = new Intent(view.getContext(), DescriptionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("abc", movies);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        }

        private void setImage() {
            txtLike.setText("Đã thích");
            txtLike.setTextColor(Color.parseColor("#F43D04"));

            imgLike.setImageResource(R.drawable.ic_like_orange);
        }
    }

public interface MovieItemListener{
        void onPostClick(long id);
}

}