package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.math.MathUtils;

public enum Drink {
    DRINK0(0, "icons/water.png", "water"),
    DRINK1(1, "icons/dobriy.png", "dobriy cola"),
    DRINK2(2, "icons/mors.png", "mors"),
    WRONGDRINK(5, "icons/badDrink.png", "wrong");

    public final int number;
    public final String texturePath;
    public final String drinkName;
    Drink(int number, String texturePath, String drinkName) {
        this.number = number;
        this.texturePath = texturePath;
        this.drinkName = drinkName;
    }

    public static Drink randomDrink() {
        Drink[] drinks = Drink.values();
        return drinks[MathUtils.random(0, drinks.length - 2)];
    }
}
