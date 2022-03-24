package com.proj.sustc.entity;

public class FavoriateProduct {
    public String model;
    public String volume;

    public FavoriateProduct(){
        model=null;
        volume=null;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
