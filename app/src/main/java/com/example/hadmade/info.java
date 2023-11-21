package com.example.hadmade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hadmade.modelo.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class info extends AppCompatActivity {
    EditText name, ciu, loca, dire, num, cod, ref;
    Button button;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mfirestore;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        name = findViewById(R.id.nombre);
        ciu = findViewById(R.id.ciudad);
        loca = findViewById(R.id.localidad);
        dire = findViewById(R.id.direccion);
        num = findViewById(R.id.numero);
        cod = findViewById(R.id.postal);
        ref = findViewById(R.id.referencias);
        button = findViewById(R.id.Aceptar);


        mAuth = FirebaseAuth.getInstance();
        mfirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = name.getText().toString().trim();
                String ciuUser = ciu.getText().toString().trim();
                String locaUser = loca.getText().toString().trim();
                String direUser = dire.getText().toString().trim();
                String numUser = num.getText().toString().trim();
                String codUser = cod.getText().toString().trim();
                String refUser = ref.getText().toString().trim();

                if (nameUser.isEmpty() && ciuUser.isEmpty() && locaUser.isEmpty() && direUser.isEmpty() && numUser.isEmpty() && codUser.isEmpty() && refUser.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ingresar datos", Toast.LENGTH_SHORT).show();
                } else {

                    postUser(nameUser,ciuUser,locaUser,direUser,numUser,codUser,refUser);


                }
            }
        });
    }
               private void postUser(String nameUser,String ciuUser,String locaUser, String direUser , String numUser,String codUser,String refUser){
                   Map<String, Object> map = new HashMap<>();
        map.put("name",nameUser);
        map.put("ciu",ciuUser);
                   map.put("loca",locaUser);
                   map.put("dire",direUser);
                   map.put("num",numUser);
                   map.put("cod",codUser);
                   map.put("ref",refUser);

        mfirestore.collection("user").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"creado exitosamente",Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al cargar",Toast.LENGTH_SHORT).show();
            }
        });
               }

}
