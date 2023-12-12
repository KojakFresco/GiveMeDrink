package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.math.MathUtils;

public enum Drink {
    DRINK0("icons/water.png", "water"),
    DRINK1("icons/dobriy.png", "dobriy cola"),
    DRINK2("icons/mors.png", "mors");

    public final String texturePath;
    public final String drinkName;
    Drink(String texturePath, String drinkName) {
        this.texturePath = texturePath;
        this.drinkName = drinkName;
    }

    public static Drink randomDrink() {
        Drink[] drinks = Drink.values();
        return drinks[MathUtils.random(0, drinks.length - 1)];
    }
    //TODO: wrong bad drinks mechanic
}
