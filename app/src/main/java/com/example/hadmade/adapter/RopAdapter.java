package com.example.hadmade.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadmade.CompraFinalizada;
import com.example.hadmade.R;
import com.example.hadmade.modelo.user;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.Map;

public class RopAdapter extends FirestoreRecyclerAdapter<user, RopAdapter.ViewHolder> {
    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private Activity activity;




    public RopAdapter(@NonNull FirestoreRecyclerOptions<user> options) {
        super(options);
        this.activity = activity;


    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull user user) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.nombre.setText(user.getName());
        viewHolder.ciudad.setText(user.getCiu());
        viewHolder.localidad.setText(user.getLoca());
        viewHolder.direccion.setText(user.getDire());
        viewHolder.numero.setText(user.getNum());
        viewHolder.postal.setText(user.getCod());
        viewHolder.referencias.setText(user.getRef());



        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(viewHolder.nombre.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_poput2))
                        .setExpanded(true,2000)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText nameUser = view.findViewById(R.id.nombre);
                EditText ciuUser = view.findViewById(R.id.ciudad);
                EditText locaUser = view.findViewById(R.id.localidad);
                EditText direUser = view.findViewById(R.id.direccion);
                EditText numUser = view.findViewById(R.id.numero);
                EditText codUser= view.findViewById(R.id.postal);
                EditText refUser = view.findViewById(R.id.referencias);

                Button btn_Update = view.findViewById(R.id.btnUpdate);

                nameUser.setText(user.getName());
                ciuUser.setText(user.getCiu());
                locaUser.setText(user.getLoca());
                direUser.setText(user.getDire());
                numUser.setText(user.getNum());
                codUser.setText(user.getCod());
                refUser.setText(user.getRef());

                dialogPlus.show();
            btn_Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", nameUser.getText().toString());
                    map.put("ciu", ciuUser.getText().toString());
                    map.put("loca", locaUser.getText().toString());
                    map.put("dire", direUser.getText().toString());
                    map.put("num", numUser.getText().toString());
                    map.put("cod", codUser.getText().toString());
                    map.put("ref", refUser.getText().toString());

                    FirebaseFirestore.getInstance().collection("user")
                            .document(getSnapshots().getSnapshot(i).getId()).update(map)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(dialogPlus.getHolderView().getContext(), "Datos exitosos", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(dialogPlus.getHolderView().getContext(), "error", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            });
                }
            });
            }
        });


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rop_single, null, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;  // Cambiado a Button
        TextView nombre, ciudad, localidad, direccion, numero, postal, referencias;
        ImageView btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            ciudad = itemView.findViewById(R.id.ciudad);
            localidad = itemView.findViewById(R.id.localidad);
            direccion = itemView.findViewById(R.id.direccion);
            numero = itemView.findViewById(R.id.numero);
            postal = itemView.findViewById(R.id.postal);
            referencias = itemView.findViewById(R.id.referencias);
            btn_edit = itemView.findViewById(R.id.btn_editar);

        }
    }


}