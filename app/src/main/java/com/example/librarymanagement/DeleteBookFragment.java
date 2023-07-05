package com.example.librarymanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DeleteBookFragment extends Fragment {

    Spinner spDeleteTitle;
    Button btnDeleteBook;
    DatabaseReference databaseReference;
    Integer titlePosition;
    ProgressBar pgDeleteBook;


    public DeleteBookFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delete_book, container, false);

        btnDeleteBook = view.findViewById(R.id.btn_delete_book);
        pgDeleteBook = view.findViewById(R.id.pg_delete_book);
        pgDeleteBook.setVisibility(View.GONE);
        spDeleteTitle = view.findViewById(R.id.sp_delete_title);
        ArrayAdapter<String> titleListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        titleListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeleteTitle.setAdapter(titleListAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        List<TitleItems> titleList = new ArrayList();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                titleList.clear();
                titleListAdapter.clear();

                for (DataSnapshot Books: snapshot.getChildren()) {
                    TitleItems titleItems = Books.getValue(TitleItems.class);
                    titleList.add(titleItems);
                    titleListAdapter.add(titleItems.getTitle());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        spDeleteTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titlePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String titleToDelete = spDeleteTitle.getItemAtPosition(titlePosition).toString();
                //deleteBook(titleToDelete);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Are you sure?");
                dialog.setMessage(spDeleteTitle.getItemAtPosition(titlePosition).toString() + " will be permanently deleted.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pgDeleteBook.setVisibility(View.VISIBLE);
                        String titleToDelete = spDeleteTitle.getItemAtPosition(titlePosition).toString();
                        deleteBook(titleToDelete);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }


        });



        // Inflate the layout for this fragment
        return view;
    }

    private void deleteBook(String titleToDelete) {
        databaseReference.child(titleToDelete).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pgDeleteBook.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Book successfully deleted", Toast.LENGTH_SHORT).show();
                    spDeleteTitle.setSelection(0);
                }
                else {
                    pgDeleteBook.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}