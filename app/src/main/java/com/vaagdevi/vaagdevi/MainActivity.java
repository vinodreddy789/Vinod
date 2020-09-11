package com.vaagdevi.vaagdevi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText EtUsermail, EtUserpass;
    Button button, button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtUsermail = findViewById(R.id.usermail);
        EtUserpass = findViewById(R.id.pass);
        Button button = findViewById(R.id.login);
        Button button2 = findViewById(R.id.create);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EtUsermail.getText().toString();
                String password = EtUserpass.getText().toString();
                if (email.isEmpty() && password.isEmpty()) {
                    EtUsermail.setError("can't be empty");
                    EtUsermail.requestFocus();
                    EtUserpass.setError("can't be empty");
                    EtUserpass.requestFocus();
                } else if (email.isEmpty()) {
                    EtUsermail.setError("can't be empty");
                    EtUsermail.requestFocus();
                    //Toast.makeText(MainActivity.this, "please enter username and password", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    EtUserpass.setError("can't be empty");
                    EtUserpass.requestFocus();
                    // Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's informatio
                                        startActivity(new Intent(MainActivity.this,Main2Activity.class));
                                        finish();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Register and Try Again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                // Check for a valid password.
                if (password.isEmpty()) {
                    EtUserpass.setError("please enter password");
                } else if (password.length() < 6) {
                    EtUserpass.setError("password is too weak");
                    EtUserpass.requestFocus();
                } else {
                }


            }
        });
    }
}


