package com.gpplworx.mopac.mopac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;


public class Splash extends Activity {

    public static final int seconds = 5;
    public static final int mseconds = seconds *1000;
    public static final int delay = 2;

    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final View view = findViewById(R.id.view_fadein);
        Animation fadein = new AlphaAnimation(1f, 0f);
        fadein.setDuration(3000);
        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });

        view.startAnimation(fadein);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(maxProgress());

        loadSplash();
    }

    public void loadSplash(){
        new CountDownTimer(mseconds,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                progress.setProgress(progress(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent login = new Intent(Splash.this, MainApp.class);
                finish();
                startActivity(login);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        }.start();
    }

    public int progress(long milliseconds){
        return (int) ((mseconds-milliseconds)/1000);
    }

    public int maxProgress(){
        return seconds - delay;
    }
}
