package com.example.hadmade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadmade.adapter.RopAdapter;
import com.example.hadmade.modelo.user;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class verUsuario extends AppCompatActivity {
    RopAdapter mAdapter;
    RecyclerView mRecycler;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    Query query;
    RecyclerView recyclerView;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistausurio);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.ver);

        button = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verUsuario.this,CompraFinalizada.class));
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (user != null) {
            // Obtener el ID del usuario actual
            String userId = user.getUid();

            // Modificar la consulta para filtrar por el ID del usuario
            query = mFirestore.collection("user").whereEqualTo("id", userId);

            FirestoreRecyclerOptions<user> firestoreRecyclerOptions =
                    new FirestoreRecyclerOptions.Builder<user>().setQuery(query, user.class).build();

            mAdapter = new RopAdapter(firestoreRecyclerOptions);

            mRecycler = findViewById(R.id.ver);
            mRecycler.setAdapter(mAdapter);


        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Detener el escuchador de FirestoreRecyclerAdapter en onStop
        mAdapter.stopListening();
    }

}
