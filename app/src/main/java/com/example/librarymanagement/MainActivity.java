package com.example.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new HomeFragment());

        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.topAppbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (id) {
                    case R.id.nav_home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.nav_add_book:
                        replaceFragment(new AddBookFragment());
                        break;
                    case R.id.nav_update_book:
                        replaceFragment(new UpdateBookFragment());
                        break;
                    case R.id.nav_delete_book:
                        replaceFragment(new DeleteBookFragment());
                        break;
                    case R.id.nav_logout:
                        mAuth.signOut();
                        Toast.makeText(MainActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LogInActivity.class));
                        break;
                    case R.id.nav_delete_acc:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Are you sure?");
                        dialog.setMessage("Your account will be permanently deleted.");
                        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Account successfully deleted.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, LogInActivity.class));
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "Account deletion error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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
                        break;
                    case R.id.nav_close_app:
                        System.exit(0);
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in. If not, open log in page.
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LogInActivity.class));
        }
    }
}