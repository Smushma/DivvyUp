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

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbarLogin;
    ProgressBar progressBarLogin; //progressBarLogin
    EditText emailLogin; //etEmailLogin
    EditText passwordLogin; //etPasswordLogin
    Button login; //btnLogin
    TextView registerLink; //tvRegisterLink
    TextView passwordResetLink;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbarLogin = findViewById(R.id.toolbarLogin);
        toolbarLogin.setTitle("Login to DivvyUp"); //Login toolbar title
        progressBarLogin = findViewById(R.id.progressBarLogin);
        emailLogin = findViewById(R.id.etEmailLogin);
        passwordLogin = findViewById(R.id.etPasswordLogin);
        login = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.tvRegisterLink);
        passwordResetLink = findViewById(R.id.tvPasswordReset);

        firebaseAuth = FirebaseAuth.getInstance();

        /*
            Login button action (User login)
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailLogin.getText().toString().isEmpty() && passwordLogin.getText().toString().isEmpty()) { //Check for empty fields
                    Toast.makeText(LoginActivity.this, "Please fill out the fields with valid input",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    progressBarLogin.setVisibility(View.VISIBLE); //Progressbar UI
                    firebaseAuth.signInWithEmailAndPassword(emailLogin.getText().toString(),
                            passwordLogin.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBarLogin.setVisibility(View.GONE); //Progressbar UI
                                    if (task.isSuccessful()) {
                                        if (firebaseAuth.getCurrentUser().isEmailVerified()) { //VERIFY USER EMAIL BEFORE THEY CAN LOGIN
                                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Please verify your email address",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        //Link to register page
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Redirect to RegisterActivity page
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //Link to password reset page
        passwordResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Redirect to PasswordResetActivity page
                Intent passwordResetIntent = new Intent(LoginActivity.this, PasswordResetActivity.class);
                LoginActivity.this.startActivity(passwordResetIntent);
            }
        });
    }
}
