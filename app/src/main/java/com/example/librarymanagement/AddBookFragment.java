package com.example.librarymanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddBookFragment extends Fragment {

    Spinner spGenre, spStatus;
    EditText etTitle, etAuthor, etRating;
    Button btnAddBook;
    ProgressBar pgbAddBook;

    String title, author, genre, rating, status;
    Integer genrePosition, statusPosition;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public AddBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        etTitle = view.findViewById(R.id.et_title);
        etAuthor = view.findViewById(R.id.et_update_author);
        etRating = view.findViewById(R.id.et_update_rating);
        btnAddBook = view.findViewById(R.id.btn_update_book);
        pgbAddBook = view.findViewById(R.id.pgb_update_book);
        pgbAddBook.setVisibility(View.GONE);

        spGenre = view.findViewById(R.id.sp_update_genre);

        ArrayAdapter<CharSequence> adapterGenre=ArrayAdapter.createFromResource(getContext(), R.array.genre, android.R.layout.simple_spinner_dropdown_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(adapterGenre);

        spStatus = view.findViewById(R.id.sp_update_status);

        ArrayAdapter<CharSequence> adapterStatus=ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_dropdown_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapterStatus);

        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genrePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genrePosition = 0;
            }
        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                statusPosition = 0;
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void addBook() {
        title = etTitle.getText().toString();
        author = etAuthor.getText().toString();
        genre = spGenre.getItemAtPosition(genrePosition).toString();
        status = spStatus.getItemAtPosition(statusPosition).toString();
        rating = etRating.getText().toString();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Enter title");
            etTitle.requestFocus();
        }
        else if (TextUtils.isEmpty(author)) {
            etAuthor.setError("Enter author");
            etAuthor.requestFocus();
        }
        else if (genrePosition == 0) {
            TextView errorText = (TextView)spGenre.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select a genre");
        }
        else if (statusPosition == 0) {
            TextView errorText = (TextView)spStatus
                    .getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select a status");
        }
        else if (TextUtils.isEmpty(rating)) {
            etRating.setError("Enter rating");
            etRating.requestFocus();
        }
        else {
            pgbAddBook.setVisibility(View.VISIBLE);
            Books books = new Books(title, author, genre, status, rating);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Books");
            databaseReference.child(title).setValue(books).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        etTitle.setText("");
                        etAuthor.setText("");
                        spGenre.setSelection(0);
                        spStatus.setSelection(0);
                        etRating.setText("");
                        pgbAddBook.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Book successfully added", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        pgbAddBook.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}