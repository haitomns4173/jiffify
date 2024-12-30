package com.haitomns.jiffy;

public class Reel {
    private String videoUrl;
    private String caption;

    public Reel(String videoUrl, String caption) {
        this.videoUrl = videoUrl;
        this.caption = caption;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getCaption() {
        return caption;
    }
}
