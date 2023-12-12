package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public enum SitPlace {
    FIRST(GameSettings.FIRST_SIT_X),
    SECOND(GameSettings.SECOND_SIT_X),
    THIRD(GameSettings.THIRD_SIT_X);

    public final int placeX;
    public boolean isOccupied;

    SitPlace(int placeX) {
        this.placeX = placeX;
        isOccupied = false;
    }

    public static SitPlace randomPlace() {
        ArrayList<SitPlace> sitPlaces = new ArrayList<>();
        for (int i = 0; i < values().length; ++i) {
            if (!values()[i].isOccupied) {
                sitPlaces.add(values()[i]);
            }
        }
        return sitPlaces.get(MathUtils.random(0, sitPlaces.size() - 1));
    }
}
