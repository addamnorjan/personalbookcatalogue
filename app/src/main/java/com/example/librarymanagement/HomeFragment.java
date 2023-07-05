package com.example.librarymanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private final List<BookItems> bookItemsList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_home);

        // Setting recyclerview size fixed for every item in the recyclerview
        recyclerView.setHasFixedSize(true);
        // Setting layout manager to the recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bookItemsList.clear();


                // Getting all children of Books root
                for (DataSnapshot Books: snapshot.child("Books").getChildren()) {

                    if (Books.hasChild("title") && Books.hasChild("author") && Books.hasChild("genre") && Books.hasChild("status") && Books.hasChild("rating")) {

                        // Getting book details and store into the list
                        final String getTitle = Books.child("title").getValue(String.class);
                        final String getAuthor = Books.child("author").getValue(String.class);
                        final String getGenre = Books.child("genre").getValue(String.class);
                        final String getStatus = Books.child("status").getValue(String.class);
                        final String getRating = Books.child("rating").getValue(String.class);

                        // Create book object with book details
                        BookItems bookItems = new BookItems(getTitle, getAuthor, getGenre, getStatus, getRating);

                        // Adding book object into list
                        bookItemsList.add(bookItems);
                    }


                }

                recyclerView.setAdapter(new RecyclerViewAdapter(bookItemsList, getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}