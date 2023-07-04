package com.jvmfrog.endportalcoords.adapter;

public class MainModel {
    private int imageResource, textResource, navigateTo;

    public MainModel(int imageResource, int textResource, int navigateTo) {
        this.imageResource = imageResource;
        this.textResource = textResource;
        this.navigateTo = navigateTo;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getTextResource() {
        return textResource;
    }

    public void setTextResource(int textResource) {
        this.textResource = textResource;
    }

    public int getNavigateTo() {
        return navigateTo;
    }

    public void setNavigateTo(int navigateTo) {
        this.navigateTo = navigateTo;
    }
}
