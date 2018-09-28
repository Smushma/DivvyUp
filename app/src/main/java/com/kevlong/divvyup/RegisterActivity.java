package com.kevlong.divvyup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbarRegister; //toolbarRegister
    ProgressBar progressBarRegister; //progressBarRegister
    EditText emailRegister; //etEmailRegister
    EditText passwordRegister; //etPasswordRegister
    Button register;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbarRegister = findViewById(R.id.toolbarRegister);
        toolbarRegister.setTitle("Register for DivvyUp"); //Register toolbar title
        progressBarRegister = findViewById(R.id.progressBarRegister);
        emailRegister = findViewById(R.id.etEmailRegister);
        passwordRegister = findViewById(R.id.etPasswordRegister);
        register = findViewById(R.id.btnRegister);

        firebaseAuth = FirebaseAuth.getInstance(); //Firebase

        /*
            Register button action (User registration)
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarRegister.setVisibility(View.VISIBLE); //Progressbar UI
                firebaseAuth.createUserWithEmailAndPassword(emailRegister.getText().toString(),
                        passwordRegister.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarRegister.setVisibility(View.GONE); //Progressbar UI
                        if (task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Registered successfully", //Successful registration
                                                        Toast.LENGTH_LONG).show();
                                                emailRegister.setText(""); //Clear fields upon successful registration
                                                passwordRegister.setText("");
                                            }
                                            else {
                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), //Error registering
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), //Error registering
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
