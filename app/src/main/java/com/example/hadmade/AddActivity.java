package com.example.hadmade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
   EditText nombre , precio ,surl;
   Button btnAdd , btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nombre = (EditText)findViewById(R.id.txtNombre);
        precio = (EditText)findViewById(R.id.txtPrecio);
        surl = (EditText)findViewById(R.id.txtImageUrl);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            insertData();
            clearAll();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void insertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("nombre",nombre.getText().toString());
        map.put("precio",precio.getText().toString());
        map.put("surl",surl.getText().toString());

        FirebaseFirestore.getInstance().collection("ropa")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddActivity.this,"Guardado",Toast.LENGTH_SHORT);
                    }
                })

        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddActivity.this,"Error",Toast.LENGTH_SHORT);
            }
        });
    }

    private void clearAll(){
        nombre.setText("");
        precio.setText("");
        surl.setText("");
    }
}