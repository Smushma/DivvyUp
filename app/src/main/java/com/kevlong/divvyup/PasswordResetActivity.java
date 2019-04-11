package com.kevlong.divvyup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbarPasswordReset; //toolbarPasswordReset
    ProgressBar progressBarPasswordReset; //progressBarPasswordReset
    EditText emailPasswordReset; //etEmailPasswordReset
    Button resetPassword; //btnPasswordReset

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        toolbarPasswordReset = findViewById(R.id.toolbarPasswordReset);
        toolbarPasswordReset.setTitle("Forgot password");
        progressBarPasswordReset = findViewById(R.id.progressBarPasswordReset);
        emailPasswordReset = findViewById(R.id.etEmailReset);
        resetPassword = findViewById(R.id.btnPasswordReset);

        firebaseAuth = FirebaseAuth.getInstance();

        /*
            Reset password button
         */
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailPasswordReset.getText().toString().isEmpty()) { //Check for empty fields
                    Toast.makeText(PasswordResetActivity.this, "Please enter a valid email",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    progressBarPasswordReset.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(emailPasswordReset.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBarPasswordReset.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PasswordResetActivity.this,
                                                "Request sent to your email", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(PasswordResetActivity.this,
                                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
