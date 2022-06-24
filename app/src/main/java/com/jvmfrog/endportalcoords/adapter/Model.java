package com.jvmfrog.endportalcoords.adapter;

public class Model {

    private String item_name;
    private String item_coordinates;

    public Model() {

    }

    public Model(String item_name, String item_coordinates) {
        this.item_name = item_name;
        this.item_coordinates = item_coordinates;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_coordinates() {
        return item_coordinates;
    }

    public void setItem_coordinates(String item_coordinates) {
        this.item_coordinates = item_coordinates;
    }
}
