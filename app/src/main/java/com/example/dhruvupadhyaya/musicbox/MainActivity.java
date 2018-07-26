package com.example.dhruvupadhyaya.musicbox;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private  SeekBar seekBar;
    private Button prevButton;
    private Button playButton;
    private Button nextButton;
    private ImageView artistImage;
    private TextView leftTime;
    private TextView rightTime;
    private Thread thread;
    private AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();


        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                leftTime.setText(String.valueOf(dateFormat.format( new Date(currentPosition))));
                rightTime.setText(String.valueOf(dateFormat.format(new Date(duration - currentPosition))));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public  void setUpUI(){

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.tokyo);

        seekBar = findViewById(R.id.seekBarId);

        prevButton = findViewById(R.id.prevButtonId);
        playButton = findViewById(R.id.playButtonId);
        nextButton = findViewById(R.id.nextButtonId);

        artistImage = findViewById(R.id.artistImageId);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.touka);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.
                create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        artistImage.setImageDrawable(roundedBitmapDrawable);



        leftTime = findViewById(R.id.leftTimeId);
        rightTime = findViewById(R.id.rightTimeId);

        prevButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.prevButtonId:{
                backMusic();
            }
            break;

            case R.id.playButtonId:{
                if (mediaPlayer.isPlaying()){
                    pauseMusic();
                }else {
                    playMusic();
                }
            }
            break;

            case R.id.nextButtonId:{
                nextMusic();
            }
            break;

        }
    }

    public void pauseMusic(){

        if (mediaPlayer != null){
            mediaPlayer.pause();
            playButton.setBackgroundResource(android.R.drawable.ic_media_play);

        }
    }
    public void playMusic(){
        if (mediaPlayer != null){
            mediaPlayer.start();
            updateThread();
            playButton.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }
    public void backMusic(){
        if (mediaPlayer.isPlaying()){
            //for now
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
        }
    }
    public void nextMusic(){
        if (mediaPlayer.isPlaying()){

            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
        }
    }
    public void updateThread(){
        thread = new Thread(){
            @Override
            public void run() {
                try{

                    while (mediaPlayer != null && mediaPlayer.isPlaying()){
                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int newPosition = mediaPlayer.getCurrentPosition();
                                int newMax = mediaPlayer.getDuration();

                                seekBar.setMax(newMax);
                                seekBar.setProgress(newPosition);

                                //Update the text
                                leftTime.setText(String.valueOf(new SimpleDateFormat("mm:ss").
                                        format(new Date(mediaPlayer.getCurrentPosition()))));
                                rightTime.setText(String.valueOf(new SimpleDateFormat("mm:ss").
                                        format(new Date(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()))));

                            }
                        });
                    }


                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onDestroy() {


        
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            thread.interrupt();
            thread = null;
        }

        super.onDestroy();
    }




    private boolean twice = false;
    @Override
    public void onBackPressed() {

        Log.d("twice","click");
        if (twice == true){
            MainActivity.this.finish();

        }
        twice = true;
        Log.d("twice"," " + twice);


        Toast.makeText(MainActivity.this,"Press BACK again to EXIT",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;

                Log.d("twice"," " + twice);
            }
        }, 3000);


        Log.d("twice"," " + twice);
    }

}

