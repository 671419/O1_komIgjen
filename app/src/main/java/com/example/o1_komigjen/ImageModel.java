package com.example.o1_komigjen;

import android.net.Uri;

public class ImageModel {
    int id;
    Uri uri;
    String name;

    public ImageModel(int id, Uri uri, String name) {
        this.id = id;
        this.uri = uri;
        this.name = name;
    }

public int getId(){
        return id;
    }

public Uri getUri(){
    return uri;
}

public String getName(){
    return name;
}


}
