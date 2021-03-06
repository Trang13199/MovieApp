package com.mytrang.moviesapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
    private ItemClick itemClick;

    SharedPreferences preferences;

    public MovieAdapter(Context context, List<Movies> arrayList, MovieItemListener movieItemListener) {
        this.context = context;
        this.arrayList = arrayList;
        mItemListener = mItemListener;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.custom_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movies movies = arrayList.get(position);

        String name = movies.getTitle();
        if (name.contains("/")) {
            String[] header = name.split("/");
            holder.title.setText(header[0]);
            holder.name.setText(header[1]);
        } else {
            holder.title.setText(movies.getTitle());
            holder.name.setText(null);
        }

        String comment = movies.getDescription();
//        if (comment.length() > 200) {
//            holder.content.setText(comment.substring(0, 200) + "...");
////            holder.content.getli
//        } else {
            holder.content.setText(comment);
//        }

        Picasso.get()
                .load(movies.getImage())
                .into(holder.imageView);

        holder.views.setText("L?????t xem: " + movies.getViews().toString());
        int id = movies.getId();

        preferences = context.getSharedPreferences("data", context.MODE_PRIVATE);
        boolean ok = preferences.getBoolean(String.valueOf(id), false);
        if (ok) {

            holder.redColor();

        } else {

            holder.whiteColor();

        }


        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = context.getSharedPreferences("data", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                if (preferences.getString("token", "").isEmpty() && preferences.getString("email", "").isEmpty()
                        && preferences.getString("password", "").isEmpty()) {
                    itemClick.onClick("token");

//                    Intent login = new Intent(view.getContext(), LoginMainActivity.class);
//                    view.getContext().startActivity(login);
                } else {

                    boolean check = preferences.getBoolean(String.valueOf(id), false);

                    if (!check) {
                        holder.redColor();
                        editor.putBoolean(String.valueOf(id), true);

                    } else {
                        holder.whiteColor();
                        editor.putBoolean(String.valueOf(id), false);
                    }
                    editor.commit();

                    Log.e("id", String.valueOf(id));

                    Log.e("name", name);
                }
            }
        });
    }

    public void updateAnswer(List<Movies> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

    public void insertArr(List<Movies> list) {
        for (Movies m : list) {
            arrayList.add(m);
        }
        notifyItemInserted(arrayList.size() - 1);
        notifyDataSetChanged();
    }

    public Movies getMovie(int adapterPosition) {
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
            views = itemView.findViewById(R.id.views);
            content = itemView.findViewById(R.id.txt_content);

            imgLike = itemView.findViewById(R.id.img_like);
            txtLike = itemView.findViewById(R.id.txt_like);

            this.mmovieItemListener = movieItemListener;
            watch = itemView.findViewById(R.id.btn_xemthem);
            watch.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_xemthem:
                    watchVideo(view);
                    break;
            }
        }

        private void whiteColor() {
            imgLike.setImageResource(R.drawable.ic_like);
            txtLike.setText("Th??ch");
            txtLike.setTextColor(Color.parseColor("#FFFFFFFF"));
        }

        private void redColor() {
            txtLike.setText("???? th??ch");
            txtLike.setTextColor(Color.parseColor("#F43D04"));
            imgLike.setImageResource(R.drawable.ic_like_orange);
        }

        private void watchVideo(View view) {
            preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
            if (preferences.getString("token", "").isEmpty() && preferences.getString("email", "").isEmpty() && preferences.getString("password", "").isEmpty()) {
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
    }

    public interface MovieItemListener {
        void onPostClick(long id);
    }

}
