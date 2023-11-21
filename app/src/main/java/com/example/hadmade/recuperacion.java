package com.example.hadmade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class recuperacion extends AppCompatActivity {
    Button recuperarButton;
    EditText emailEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);

        recuperarButton = findViewById(R.id.recuperar);
        emailEdit = findViewById(R.id.email);

        recuperarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }
    public void validate(){
        String email = emailEdit.getText().toString().trim();

        if (email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdit.setError("correo invalido");
            return;
        }
        sendEmail(email);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        Intent intent = new Intent(recuperacion.this,login.class);
        startActivity(intent);
        finish();
    }

    public void sendEmail(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(recuperacion.this ,"Correo enviado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(recuperacion.this, login.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(recuperacion.this,"correo invalido", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

}