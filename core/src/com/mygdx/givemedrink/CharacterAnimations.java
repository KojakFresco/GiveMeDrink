package com.mygdx.givemedrink;

import java.util.ArrayList;

public class CharacterAnimations {

    public ArrayList<ArrayList<ArrayList<String>>> charactersPathsList;

    public ArrayList<ArrayList<String>> character1PathsList;
    public ArrayList<String> character1WalkLeftList;
    public ArrayList<String> character1SitList;
    public ArrayList<String> character1WalkRightList;


    public CharacterAnimations() {
        charactersPathsList = new ArrayList<>();

        character1PathsList = new ArrayList<>();
        character1WalkLeftList = new ArrayList<>();
        character1SitList = new ArrayList<>();
        character1WalkRightList = new ArrayList<>();

        for (int i = 0; i < 2; ++i)
            character1WalkLeftList.add("tiles/characters/test" + i + ".png");
        for (int i = 0; i < 2; ++i)
            character1SitList.add("tiles/characters/test" + i + ".png");
        for (int i = 0; i < 2; ++i)
            character1WalkRightList.add("tiles/characters/test" + i + ".png");


        character1PathsList.add(character1WalkLeftList);
        character1PathsList.add(character1SitList);
        character1PathsList.add(character1WalkRightList);

        charactersPathsList.add(character1PathsList);
    }
}
