package com.yala.sushant.ehr_backend.model;

/**
 * Created by sushant on 11/17/17.
 */

public class Article {



    private String title;
    private String body;
    private String image;
    private String name;
    private String uid;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


public Article(){}


    public Article(String title, String body, String image, String name) {
        this.title = title;
        this.body = body;
        this.image = image;

        this.name = name;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
