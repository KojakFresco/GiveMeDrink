package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.math.MathUtils;

public enum Glass {
    DRINK0("icons/drink0"),
    DRINK1("icons/drink1"),
    DRINK2("icons/drink2");

    public final String texturePath;

    Glass(String texturePath) {
        this.texturePath = texturePath;
    }

    public static Glass randomGlass() {
        Glass[] glasses = Glass.values();
        return glasses[MathUtils.random(0, glasses.length - 1)];
    }
}
