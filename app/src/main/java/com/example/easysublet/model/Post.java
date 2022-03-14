package com.example.easysublet.model;

import androidx.annotation.Nullable;

public class Post {

    @Nullable
    private String title;

    public Post (String title) {
        this.title = title;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public void setTitle(String title) {
        this.title = title;
    }
}
