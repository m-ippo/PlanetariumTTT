/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import static planetarium.contents.corpicelesti.enums.TipiCorpiCelesti.LUNA;
import static planetarium.contents.corpicelesti.enums.TipiCorpiCelesti.PIANETA;
import static planetarium.contents.corpicelesti.enums.TipiCorpiCelesti.STELLA;

/**
 *
 * @author TTT
 */
public class NamePicker {

    private final Stack<String> stelle = new Stack<>();
    private final Stack<String> pianeti = new Stack<>();
    private final Stack<String> lune = new Stack<>();

    private static NamePicker instance;

    private NamePicker() {
        init();
    }

    private void init() {
        String stars = getClass().getClassLoader().getResource("planetarium/resources/names/stelle").getFile();
        String planets = getClass().getClassLoader().getResource("planetarium/resources/names/pianeti").getFile();
        String moons = getClass().getClassLoader().getResource("planetarium/resources/names/lune").getFile();
        try (FileInputStream fr_s = new FileInputStream(new File(stars));
                FileInputStream fr_p = new FileInputStream(new File(planets));
                FileInputStream fr_m = new FileInputStream(new File(moons))) {
            stelle.addAll(Arrays.asList(new String(fr_s.readAllBytes()).split("\n")));
            pianeti.addAll(Arrays.asList(new String(fr_p.readAllBytes()).split("\n")));
            lune.addAll(Arrays.asList(new String(fr_m.readAllBytes()).split("\n")));
            Collections.shuffle(lune);
            Collections.shuffle(pianeti);
            Collections.shuffle(stelle);
        } catch (IOException ioe) {

        }
    }

    public static NamePicker getInstance() {
        if (instance == null) {
            instance = new NamePicker();
        }
        return instance;
    }

    public String getName(TipiCorpiCelesti ct) {
        switch (ct) {
            default:
                //case ASTEROIDE:
                return getRandomName();
            case LUNA:
                return lune.size() > 0 ? lune.pop() : getRandomName();
            case PIANETA:
                return pianeti.size() > 0 ? pianeti.pop() : getRandomName();
            case STELLA:
                return stelle.size() > 0 ? stelle.pop() : getRandomName();
            /*case SISTEMA:
                return "Sys:" + getRandomName();
            case MORTE_NERA:
                return "Death Star";
            case TESLA_ROADSTER:
                return "Tesla Roadster";*/
        }
    }

    private String getRandomName() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'A');
        return c + "-" + (int) (Math.random() * (5000 - 100 + 1) + 100);
    }

}
