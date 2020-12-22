package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covidtracker.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1500;
    ImageView logoImage;
    Animation rotateanimation, fadeoutanimation, zoominanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        logoImage = findViewById(R.id.imageView);


        rotateanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        fadeoutanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        zoominanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        AnimationSet s = new AnimationSet(false);
        s.addAnimation(rotateanimation);
        s.addAnimation(fadeoutanimation);
        s.addAnimation(zoominanimation);
        logoImage.startAnimation(s);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}