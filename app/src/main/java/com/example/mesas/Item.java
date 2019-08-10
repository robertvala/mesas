package com.example.mesas;

public class Item {
    private String id;
    private int ImageResoruce;
    private String mText1;
    private String mText2;

    public Item(String id,int ImageResource, String mText1, String mText2){
        this.id=id;
        this.ImageResoruce=ImageResource;
        this.mText1=mText1;
        this.mText2=mText2;
    }

    public String getmText2() {
        return mText2;
    }

    public String getmText1() {
        return mText1;
    }

    public int getImageResoruce() {
        return ImageResoruce;
    }
}
