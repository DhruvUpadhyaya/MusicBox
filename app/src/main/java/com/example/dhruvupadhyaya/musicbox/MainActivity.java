package com.example.dhruvupadhyaya.musicbox;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm : ss");
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                leftTime.setText(simpleDateFormat.format(new Date(currentPosition)));
                rightTime.setText(simpleDateFormat.format(new Date(duration - currentPosition)));
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
       // artistImage.setImageResource(R.drawable.touka);

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
            playButton.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }
}
