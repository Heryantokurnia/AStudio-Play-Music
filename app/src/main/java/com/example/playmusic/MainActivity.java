package com.example.playmusic;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnPlay;
    private boolean isPlaying = false;
    private List<Music> musicList;
    private MediaPlayer mediaPlayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SeekBar seekBar = findViewById(R.id.seekBar);
        ImageButton btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        ImageButton btnNext = findViewById(R.id.btnNext);

        RecyclerView musicRecyclerView = findViewById(R.id.musicRecyclerView);
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        musicList = new ArrayList<>();
        musicList.add(new Music("Boom", R.raw.boom));
        musicList.add(new Music("Smells like teen spirit", R.raw.smells_like_teen_spirit));
        musicList.add(new Music("Tek it", R.raw.tek_it));

        MusicAdapter adapter = new MusicAdapter(musicList, position -> playMusic(musicList.get(position)));
        musicRecyclerView.setAdapter(adapter);

        btnPlay.setOnClickListener(v -> {
            if (isPlaying) {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                }
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                isPlaying = false;
            } else {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                isPlaying = true;
            }
        });
    }

    private void playMusic(Music music) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(this, music.getAudioResourceId());
        mediaPlayer.start();
        btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        isPlaying = true;

        mediaPlayer.setOnCompletionListener(mp -> {
            btnPlay.setImageResource(android.R.drawable.ic_media_play);
            isPlaying = false;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
