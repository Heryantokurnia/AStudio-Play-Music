package com.example.playmusic;

public class Music {
    private final String title;

    public Music(String title, int ignoredBoom) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getAudioResourceId() {
        return 0;
    }
}
