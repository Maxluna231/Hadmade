package com.example.hadmade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hadmade.modelo.RopaModel;

public class DescripcionActivity extends AppCompatActivity {
    TextView nombre, precio;
    ImageView imagen;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        Log.d("DescripcionActivity", "Entrando en onCreate");
        RopaModel element = (RopaModel) getIntent().getSerializableExtra("ropa");
        if (element != null) {
        nombre = findViewById(R.id.nombre);
        precio = findViewById(R.id.precio);
        imagen = findViewById(R.id.imagen);
        button = findViewById(R.id.button);

        nombre.setText(element.getNombre());
        precio.setText(element.getPrecio());
        Glide.with(this)
                .load(element.getSurl())
                .into(imagen);
        } else {

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DescripcionActivity.this, verUsuario.class));
                Toast.makeText(DescripcionActivity.this, "Confirmar Datos", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
