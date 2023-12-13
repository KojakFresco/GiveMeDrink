package com.mygdx.givemedrink;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class CharacterAnimations {

    public static ArrayList<ArrayList<ArrayList<String>>> charactersPathsList;
    public static ArrayList<ArrayList<Sound>>  characterSoundsList;

    public CharacterAnimations() {
        charactersPathsList = new ArrayList<>();
        characterSoundsList = new ArrayList<>();

        ArrayList<ArrayList<String>> character1PathsList = new ArrayList<>();

        ArrayList<String> character1WalkLeftList = new ArrayList<>();
        ArrayList<String> character1AskingList = new ArrayList<>();
        ArrayList<String> character1SitList = new ArrayList<>();
        ArrayList<String> character1WalkRightList = new ArrayList<>();
        ArrayList<Sound> character1SoundsList = new ArrayList<>();

        ArrayList<ArrayList<String>> bikerPathsList = new ArrayList<>();

        ArrayList<String> bikerWalkLeftList = new ArrayList<>();
        ArrayList<String> bikerAskingList = new ArrayList<>();
        ArrayList<String> bikerSitList = new ArrayList<>();
        ArrayList<String> bikerWalkRightList = new ArrayList<>();

        for (int i = 0; i < 2; ++i)
            character1WalkLeftList.add("tiles/characters/test" + i + ".png");
        for (int i = 2; i > 0; --i)
            character1AskingList.add("tiles/characters/test" + i + ".png");
        for (int i = 0; i < 2; ++i)
            character1SitList.add("tiles/characters/test" + i + ".png");
        for (int i = 0; i < 2; ++i)
            character1WalkRightList.add("tiles/characters/testOut" + i + ".png");
        for (int i = 0; i < 3; ++i)
            character1SoundsList.add(Gdx.audio.newSound(
                    Gdx.files.internal("sounds/testSound" + i + ".mp3")));

        for (int i = 0; i < 2; ++i)
            bikerWalkLeftList.add("tiles/characters/biker/biker" + i + ".png");
        for (int i = 1; i >= 0; --i)
            bikerAskingList.add("tiles/characters/biker/biker" + i + ".png");
        for (int i = 0; i < 2; ++i)
            bikerSitList.add("tiles/characters/biker/biker" + i + ".png");
        for (int i = 0; i < 2; ++i)
            bikerWalkRightList.add("tiles/characters/biker/bikerOut" + i + ".png");


        character1PathsList.add(character1WalkLeftList);
        character1PathsList.add(character1AskingList);
        character1PathsList.add(character1SitList);
        character1PathsList.add(character1WalkRightList);
        characterSoundsList.add(character1SoundsList);

        charactersPathsList.add(character1PathsList);

        bikerPathsList.add(bikerWalkLeftList);
        bikerPathsList.add(bikerAskingList);
        bikerPathsList.add(bikerSitList);
        bikerPathsList.add(bikerWalkRightList);
        characterSoundsList.add(character1SoundsList);

        charactersPathsList.add(bikerPathsList);
    }

    public static ArrayList<ArrayList<String>> randomCharacter() {
        return charactersPathsList.get(MathUtils.random(0, charactersPathsList.size() - 1));
    }

    public  static int getIndexOfCharacter(ArrayList<ArrayList<String>> character) {
        return charactersPathsList.indexOf(character);
    }
}
