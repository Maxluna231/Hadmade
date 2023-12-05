package com.example.hadmade;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadmade.adapter.RopaAdapter;
import com.example.hadmade.modelo.RopaModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class firebasevideo extends AppCompatActivity {
    RecyclerView recyclerView;

    RopaAdapter ropaAdapter;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasevideo);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirestoreRecyclerOptions<RopaModel> options =
                new FirestoreRecyclerOptions.Builder<RopaModel>()
                        .setQuery(FirebaseFirestore.getInstance().collection("ropa"), RopaModel.class)
                        .build();

        ropaAdapter = new RopaAdapter(options);
                recyclerView.setAdapter(ropaAdapter);

                floatingActionButton =(FloatingActionButton)findViewById(R.id.floatingActionButton);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(firebasevideo.this,AddActivity.class));
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ropaAdapter.startListening();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.menu.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtsearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void txtsearch(String str){

        FirestoreRecyclerOptions<RopaModel> options =
                new FirestoreRecyclerOptions.Builder<RopaModel>()
                        .setQuery(FirebaseFirestore.getInstance().collection("ropa").orderBy("nombre").startAt(str).endAt(str+"~"), RopaModel.class)
                        .build();

        ropaAdapter = new RopaAdapter(options);
        ropaAdapter.startListening();
        recyclerView.setAdapter(ropaAdapter);
    }
}