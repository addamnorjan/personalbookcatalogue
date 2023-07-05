package com.example.librarymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final List<BookItems> bookItems;
    private final Context context;

    public RecyclerViewAdapter(List<BookItems> bookItems, Context context) {
        this.bookItems = bookItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        BookItems bookItems1 = bookItems.get(position);

        holder.tvTitle.setText("Title: " + bookItems1.getTitle());
        holder.tvAuthor.setText("Author: " + bookItems1.getAuthor());
        holder.tvGenre.setText("Genre: " + bookItems1.getGenre());
        holder.tvStatus.setText("Status: " + bookItems1.getStatus());
        holder.tvRating.setText("My rating: " + bookItems1.getRating());
    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }

    // To hold view reference for every item in the recyclerview
    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle, tvAuthor, tvGenre, tvStatus, tvRating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvRating = itemView.findViewById(R.id.tv_rating);
        }
    }
}
