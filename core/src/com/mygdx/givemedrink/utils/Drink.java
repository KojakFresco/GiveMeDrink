package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.math.MathUtils;

public enum Drink {
    DRINK0("icons/water.png", "water", 0.05),
    DRINK1("icons/dobriy.png", "dobriy cola", 0.05),
    DRINK2("icons/mors.png", "mors", 0.05);

    public final String texturePath;
    public final String drinkName;
    public final double frictionFactor;

    Drink(String texturePath, String drinkName, double frictionFactor) {
        this.texturePath = texturePath;
        this.drinkName = drinkName;
        this.frictionFactor = frictionFactor;
    }

    public static Drink randomDrink() {
        Drink[] drinks = Drink.values();
        return drinks[MathUtils.random(0, drinks.length - 1)];
    }
}
