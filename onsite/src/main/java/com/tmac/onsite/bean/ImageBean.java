package com.tmac.onsite.bean;

import android.graphics.Bitmap;

/**
 * Created by user on 16/11/7.
 */

public class ImageBean {

    private Bitmap bitmap;

    public ImageBean() {
    }

    public ImageBean(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "bitmap=" + bitmap +
                '}';
    }
}
