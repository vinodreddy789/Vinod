package com.vaagdevi.vaagdevi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText inputmail;
    private EditText inputpassword, username;
    Button register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        databaseReference = FirebaseDatabase.getInstance().getReference("cantten");
        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.frst);
        inputmail = findViewById(R.id.mail);
        inputpassword = findViewById(R.id.passw);
        register = findViewById(R.id.reg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = inputpassword.getText().toString();
                String email = inputmail.getText().toString();
                String users = username.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String usersname =
                        username.getText().toString();
                final String emailreg =
                        inputmail.getText().toString();
                final String passreg =
                        inputpassword.getText().toString();
//                if (password.length() < 6) {
//                       inputpassword.setError("password is too weak");
//                            inputpassword.requestFocus();
//                      } else if (email.matches(emailPattern)) {
//                                Toast.makeText(Rigister.this, "Invalid email address", Toast.LENGTH_SHORT).show();
//                         } else {
//                               startActivity(new Intent(Rigister.this, MainActivity.class).putExtra("email", email));
//                                 finish();
/////
////
////                }
//                //{
//                if (password.isEmpty() && email.isEmpty()) {
//                    inputpassword.setError("can't");
//                    inputpassword.requestFocus();
//                    username.setError("can't");
//                    username.requestFocus();
//                    inputmail.setError("can't");
//                    inputmail.requestFocus();


//                    } else {
//                     startActivity(new Intent(Rigister.this, MainActivity.class).putExtra("email", email));
//                       finish();
                // }

                firebaseAuth.createUserWithEmailAndPassword(emailreg,
                        passreg).addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            RegInformation reginformation = new RegInformation(usersname, emailreg, passreg);
                            FirebaseDatabase.getInstance().getReference(databaseReference.getKey()).child(firebaseAuth.getCurrentUser().getUid()).setValue(reginformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Main2Activity.this, "Hii" + usersname + "...Continue here!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Main2Activity.this, MainActivity.class));
                                    finish();
                                }
                            });
                        }
                    }
                });


                final FirebaseAuth mAuth;
//Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(Main2Activity.this, MainActivity.class));
                                    finish();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //  updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Main2Activity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String emailss = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();

                    // Check if user's email is verified
                    boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getIdToken() instead.
                    String uid = user.getUid();
                }
            }


        });
    }
}