package com.example.easysublet.model;

public class Post {
    private int image;
    private String title;
    private int idx;

    public Post (String title, int image, int idx) {
        this.title = title;
        this.image = image;
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }

    public int getIdx() {
        return this.idx;
    }

}
