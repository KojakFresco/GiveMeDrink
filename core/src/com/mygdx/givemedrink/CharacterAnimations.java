package com.mygdx.givemedrink;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Random;

public class CharacterAnimations {

    public static ArrayList<ArrayList<ArrayList<String>>> charactersPathsList;
    public static ArrayList<ArrayList<Sound>>  characterSoundsList;

    static Random random = new Random(System.currentTimeMillis());

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

        ArrayList<ArrayList<String>> walterPathsList = new ArrayList<>();

        ArrayList<String> walterWalkLeftList = new ArrayList<>();
        ArrayList<String> walterAskingList = new ArrayList<>();
        ArrayList<String> walterSitList = new ArrayList<>();
        ArrayList<String> walterWalkRightList = new ArrayList<>();

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

        for (int i = 0; i < 2; ++i)
            walterWalkLeftList.add("tiles/characters/walter/walter" + i + ".png");
        for (int i = 2; i > 0; --i)
            walterAskingList.add("tiles/characters/walter/walter" + i + ".png");
        for (int i = 0; i < 2; ++i)
            walterSitList.add("tiles/characters/walter/walter" + i + ".png");
        for (int i = 0; i < 2; ++i)
            walterWalkRightList.add("tiles/characters/walter/walterOut" + i + ".png");


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

        walterPathsList.add(walterWalkLeftList);
        walterPathsList.add(walterAskingList);
        walterPathsList.add(walterSitList);
        walterPathsList.add(walterWalkRightList);
        characterSoundsList.add(character1SoundsList);

        charactersPathsList.add(walterPathsList);
    }

    public static ArrayList<ArrayList<String>> randomCharacter() {
        System.out.println("random:" + random.nextInt(charactersPathsList.size()));
        return charactersPathsList.get(random.nextInt(charactersPathsList.size()));
    }

    public  static int getIndexOfCharacter(ArrayList<ArrayList<String>> character) {
        return charactersPathsList.indexOf(character);
    }
}
