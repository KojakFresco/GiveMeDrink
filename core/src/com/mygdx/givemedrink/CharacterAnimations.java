package com.mygdx.givemedrink;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class CharacterAnimations {

    public static ArrayList<ArrayList<ArrayList<String>>> charactersPathsList;

    public ArrayList<ArrayList<String>> character1PathsList;


    public CharacterAnimations() {
        charactersPathsList = new ArrayList<>();

        character1PathsList = new ArrayList<>();

        ArrayList<String> character1WalkLeftList = new ArrayList<>();
        ArrayList<String> character1AskingList = new ArrayList<>();
        ArrayList<String> character1SitList = new ArrayList<>();
        ArrayList<String> character1WalkRightList = new ArrayList<>();

        for (int i = 0; i < 2; ++i)
            character1WalkLeftList.add("tiles/characters/test" + i + ".png");
        for (int i = 2; i > 0; --i)
            character1AskingList.add("tiles/characters/test" + i + ".png");
        for (int i = 0; i < 2; ++i)
            character1SitList.add("tiles/characters/test" + i + ".png");
        for (int i = 0; i < 2; ++i)
            character1WalkRightList.add("tiles/characters/testOut" + i + ".png");


        character1PathsList.add(character1WalkLeftList);
        character1PathsList.add(character1AskingList);
        character1PathsList.add(character1SitList);
        character1PathsList.add(character1WalkRightList);

        charactersPathsList.add(character1PathsList);
    }

    public static ArrayList<ArrayList<String>> randomCharacter() {
        return charactersPathsList.get(MathUtils.random(0, charactersPathsList.size() - 1));
    }
}
