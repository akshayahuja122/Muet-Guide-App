package com.bukhari.fyp;

public class PointCardData {
    private int mImageResource;
    private String title;
    private String subTitle;

    public PointCardData(int mImageResource, String title, String subTitle) {
        this.mImageResource = mImageResource;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
