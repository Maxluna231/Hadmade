package com.example.hadmade;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.hadmade.R.id.nav_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.hadmade.adapter.RoAdapter;
import com.example.hadmade.modelo.RopaModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RoAdapter roAdapter;
    ImageView imageView;

    RecyclerView recyclerView;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imageView);
        imageSlider = findViewById(R.id.image_slider);

        List<SlideModel> slideModelList = new ArrayList<>();

        slideModelList.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(R.drawable.images, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));

        imageSlider.setImageList(slideModelList);

        imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE);
        imageSlider.startSliding(2000);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.idRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        FirestoreRecyclerOptions<RopaModel> options =
                new FirestoreRecyclerOptions.Builder<RopaModel>()
                        .setQuery(FirebaseFirestore.getInstance().collection("ropa"), RopaModel.class)
                        .build();

        roAdapter = new RoAdapter(options, new RoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RopaModel item) {
                moveToDescripcion(item);
            }
        });
        recyclerView.setAdapter(roAdapter);
    }



        @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    public void moveToDescripcion(RopaModel item) {
        Intent intent = new Intent(this, DescripcionActivity.class);
        intent.putExtra("ropa",(item));
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if(id == R.id.hogar) {

            } else if (id == R.id.nav_salir) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, login.class));
            } else if (id == R.id.Usuario) {
                Toast.makeText(this, "Usuario", LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,verUsuario.class));

            } else if (id == R.id.anadir) {
                Toast.makeText(this, "Usuario", LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, firebasevideo.class));
            }else if (id == R.id.nav_info){
                startActivity(new Intent(MainActivity.this, MainActivity3.class));
            }



        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);  // Corregir aquí
        SearchView searchView = (SearchView) item.getActionView();

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

    private void txtsearch(String str) {

        FirestoreRecyclerOptions<RopaModel> options =
                new FirestoreRecyclerOptions.Builder<RopaModel>()
                        .setQuery(FirebaseFirestore.getInstance().collection("ropa").orderBy("nombre").startAt(str).endAt(str + "~"), RopaModel.class)
                        .build();

        roAdapter = new RoAdapter(options, new RoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RopaModel item) {
                moveToDescripcion(item);
            }
        });
        roAdapter.startListening();
        recyclerView.setAdapter(roAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        roAdapter.startListening();
    }



}
