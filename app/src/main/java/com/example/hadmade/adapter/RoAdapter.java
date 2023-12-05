package com.example.hadmade.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hadmade.R;
import com.example.hadmade.modelo.RopaModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class RoAdapter extends FirestoreRecyclerAdapter<RopaModel, RoAdapter.myViewHolder> {


    final RoAdapter.OnItemClickListener listener; // Fix the type here

    public interface OnItemClickListener {
        void onItemClick(RopaModel item);
    }
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RoAdapter(@NonNull FirestoreRecyclerOptions<RopaModel> options, RoAdapter.OnItemClickListener listener ) {
        super(options);
        this.listener = listener;


    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull RopaModel model) {
        holder.nombre.setText(model.getNombre());
        holder.precio.setText(model.getPrecio());

        Glide.with(holder.imageView.getContext())
                .load(model.getSurl())
                .placeholder(com.firebase.ui.firestore.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.firestore.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(model);
            }
        });
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nombre,precio;

        public myViewHolder(@NonNull View ItemView){
            super(ItemView);

            imageView = (ImageView)itemView.findViewById(R.id.image);
            nombre = (TextView)itemView.findViewById(R.id.nombre);
            precio = (TextView)itemView.findViewById(R.id.precio);
        }
}

}