package com.example.librarymanagement;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UpdateBookFragment extends Fragment {


    Spinner spTitle, spGenre, spStatus;
    EditText etAuthor, etRating;
    Button btnUpdateBook;
    ProgressBar progressBar;

    DatabaseReference databaseReference, ref;
    Integer titlePosition, newGenrePosition, newStatusPosition, oldGenrePosition, oldStatusPosition;

    //String newTitle, newAuthor, newGenre, newStatus, newRating;
    String oldAuthor, oldGenre, oldStatus, oldRating;
    String title, author, genre, status, rating;




    public UpdateBookFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_book, container, false);


        spTitle = view.findViewById(R.id.sp_update_title);
        spGenre = view.findViewById(R.id.sp_update_genre);
        spStatus = view.findViewById(R.id.sp_update_status);
        etAuthor = view.findViewById(R.id.et_update_author);
        etRating = view.findViewById(R.id.et_update_rating);
        btnUpdateBook = view.findViewById(R.id.btn_update_book);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapterGenre=ArrayAdapter.createFromResource(getContext(), R.array.genre, android.R.layout.simple_spinner_dropdown_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(adapterGenre);

        ArrayAdapter<CharSequence> adapterStatus=ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_dropdown_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapterStatus);

        ArrayAdapter<String> titleListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        titleListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTitle.setAdapter(titleListAdapter);

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


        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titlePosition = position;
                ref = databaseReference.child(spTitle.getItemAtPosition(titlePosition).toString());

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        title = snapshot.child("title").getValue(String.class);
                        etAuthor.setText(snapshot.child("author").getValue(String.class));
                        oldAuthor = snapshot.child("author").getValue(String.class);
                        spGenre.setSelection(adapterGenre.getPosition(snapshot.child("genre").getValue(String.class)));
                        oldGenre = snapshot.child("genre").getValue(String.class);
                        oldGenrePosition = adapterGenre.getPosition(snapshot.child("genre").getValue(String.class));
                        spStatus.setSelection(adapterStatus.getPosition(snapshot.child("status").getValue(String.class)));
                        oldStatus = snapshot.child("status").getValue(String.class);
                        oldStatusPosition = adapterStatus.getPosition(snapshot.child("status").getValue(String.class));
                        etRating.setText(snapshot.child("rating").getValue(String.class));
                        oldRating = snapshot.child("rating").getValue(String.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //newTitle = etTitle.getText().toString();
        //newAuthor = etAuthor.getText().toString();

        /*
        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newGenre = spGenre.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newGenre = old;
            }
        });*/






        //newGenre = spGenre.getItemAtPosition(newGenrePosition).toString();

        /*
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newStatusPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newStatusPosition = oldStatusPosition;
            }
        });

         */



        //spStatus.getItemAtPosition(newStatusPosition).toString();





        //newRating = etRating.getText().toString();



        /*
        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTitleChanged() || isAuthorChanged() || isGenreChanged() || isStatusChanged() || isRatingChanged()) {
                    Toast.makeText(getContext(), "Book successfully updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "No changes made", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                newAuthor = etAuthor.getText().toString();


                spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        newGenre = spGenre.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        newGenre = oldGenre;
                    }
                });
                //newGenre = spGenre.getItemAtPosition(newGenrePosition).toString();


                spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        newStatus = spStatus.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        newStatus = oldStatus;
                    }
                });
                //newStatus = spStatus.getItemAtPosition(newStatusPosition).toString();



                newRating = etRating.getText().toString();

                if (isAuthorChanged() || isGenreChanged() || isStatusChanged() || isRatingChanged()) {
                    Toast.makeText(getContext(), "Book successfully updated", Toast.LENGTH_SHORT).show();
                }
                else if (!isAuthorChanged() && !isGenreChanged() && !isStatusChanged() && !isRatingChanged()) {
                    Toast.makeText(getContext(), "No changes made", Toast.LENGTH_SHORT).show();
                }

                 */

                author = etAuthor.getText().toString();
                genre = spGenre.getSelectedItem().toString();
                status = spStatus.getSelectedItem().toString();

                /*
                spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        genre = spGenre.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        genre = oldGenre;
                    }
                });

                spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        status = spStatus.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        status = oldStatus;
                    }
                });

                 */

                rating = etRating.getText().toString();

                /*
                if (!author.equals(oldAuthor) || !genre.equals(oldGenre) || !status.equals(oldStatus) || !rating.equals(oldRating)) {
                    updateData(title, author, genre, status, rating);
                }
                if (author.equals(oldAuthor) && genre.equals(oldGenre) && status.equals(oldStatus) && rating.equals(oldRating)) {
                    Toast.makeText(getContext(), "No changes made", Toast.LENGTH_SHORT).show();
                }*/

                if (TextUtils.isEmpty(author)) {
                    etAuthor.setError("Enter author");
                    etAuthor.requestFocus();
                }
                else if (TextUtils.isEmpty(rating)) {
                    etRating.setError("Enter author");
                    etRating.requestFocus();
                }
                else if (author.equals(oldAuthor) && genre.equals(oldGenre) && status.equals(oldStatus) && rating.equals(oldRating)) {
                    Toast.makeText(getContext(), "No changes made", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    updateData(title, author, genre, status, rating);
                }

            }
        });









        // Inflate the layout for this fragment
        return view;


    }

    private void updateData(String title, String author, String genre, String status, String rating) {
        HashMap book = new HashMap();
        book.put("author", author);
        book.put("genre", genre);
        book.put("status", status);
        book.put("rating", rating);

        databaseReference.child(title).updateChildren(book).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Book successfully updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    /*
    public boolean isAuthorChanged() {
        if (!newAuthor.equals(oldAuthor)) {
            ref.child("author").setValue(newAuthor);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isGenreChanged() {
        if (!newGenre.equals(oldGenre)) {
            ref.child("genre").setValue(newGenre);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isStatusChanged() {
        if (!newStatus.equals(oldStatus)) {
            ref.child("status").setValue(newStatus);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isRatingChanged() {
        if (!newRating.equals(oldRating)) {
            ref.child("rating").setValue(newRating);
            return true;
        }
        else {
            return false;
        }
    }

     */







}