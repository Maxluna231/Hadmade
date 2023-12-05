package com.example.hadmade;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageSlider = findViewById(R.id.image_slider);

        List<SlideModel>slideModelList = new ArrayList<>();

        slideModelList.add(new SlideModel(R.drawable.logo, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(R.drawable.images, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));

        imageSlider.setImageList(slideModelList);

        imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE);
        imageSlider.startSliding(2000);



    }
}