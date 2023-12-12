package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.math.MathUtils;

public enum SitPlace {
    FIRST(GameSettings.FIRST_SIT_X),
    SECOND(GameSettings.SECOND_SIT_X),
    THIRD(GameSettings.THIRD_SIT_X);

    public final int placeX;

    SitPlace(int placeX) {
        this.placeX = placeX;
    }

    public static SitPlace randomPlace() {
        SitPlace[] sitPlaces = values();
        return sitPlaces[MathUtils.random(0, sitPlaces.length - 1)];
    }
}
