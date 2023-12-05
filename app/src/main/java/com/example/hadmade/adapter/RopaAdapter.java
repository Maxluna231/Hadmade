package com.example.hadmade.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.bumptech.glide.Glide;
import com.example.hadmade.R;
import com.example.hadmade.modelo.RopaModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RopaAdapter extends FirestoreRecyclerAdapter<RopaModel, RopaAdapter.myViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RopaAdapter(@NonNull FirestoreRecyclerOptions<RopaModel> options ) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull RopaAdapter.myViewHolder holder, final int position, @NonNull RopaModel model) {
        holder.nombre.setText(model.getNombre());
        holder.precio.setText(model.getPrecio());

        Glide.with(holder.imageView.getContext())
                .load(model.getSurl())
                .placeholder(com.firebase.ui.firestore.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.firestore.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imageView);


        holder.btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageView.getContext())
                .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();



                View view = dialogPlus.getHolderView();
                EditText nombre = view.findViewById(R.id.txtNombre);
                EditText precio = view.findViewById(R.id.txtPrecio);
                EditText surl = view.findViewById(R.id.txtImageUrl);

                Button btn_Update = view.findViewById(R.id.btnUpdate);

                nombre.setText(model.getNombre());
                precio.setText(model.getPrecio());
                surl.setText(model.getSurl());

                dialogPlus.show();

                btn_Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("nombre",nombre.getText().toString());
                        map.put("precio",precio.getText().toString());
                        map.put("surl",surl.getText().toString());

                        FirebaseFirestore.getInstance().collection("ropa")
                                .document(getSnapshots().getSnapshot(position).getId()).update(map)

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nombre.getContext(), "Datos exitosos", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(holder.nombre.getContext(), "error", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });
                    }
                });


            }
        });

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre.getContext());
                builder.setTitle("No se");
                builder.setMessage("Borrar");

                builder.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseFirestore.getInstance().collection("ropa")
                                .document(getSnapshots().getSnapshot(position).getId()).delete();

                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.nombre.getContext(), "Cancelar", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }



    @NonNull
    @Override
    public RopaAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_rop,parent,false);
        return new RopaAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nombre,precio;

        Button btn_Edit,btn_Delete;

        public myViewHolder(@NonNull View ItemView){
            super(ItemView);

            imageView = (ImageView)itemView.findViewById(R.id.image);
            nombre = (TextView)itemView.findViewById(R.id.nombre);
            precio = (TextView)itemView.findViewById(R.id.precio);

            btn_Edit = (Button)itemView.findViewById(R.id.btnEdit);
            btn_Delete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }

}
