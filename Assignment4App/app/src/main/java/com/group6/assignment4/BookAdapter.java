/*
  Filename: BookAdapter.java
  Student's Name: Bittu Patel
  Student ID: 200556453
  Date: December 06, 2023
*/
package com.group6.assignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private final List<Book> bookList;
    private OnItemClickListener listener;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.textViewName.setText(book.getName());
        holder.textViewAuthor.setText(book.getAuthor());
        holder.textViewRating.setText(book.getRating().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(book);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAuthor;
        TextView textViewRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.titleTextView);
            textViewAuthor = itemView.findViewById(R.id.authorTextView);
            textViewRating = itemView.findViewById(R.id.ratingTextView);
        }
    }
}
