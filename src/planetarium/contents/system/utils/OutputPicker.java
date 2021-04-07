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
import java.util.Stack;

/**
 *
 * @author TTT
 */
public class OutputPicker {

    private final Stack<String> created = new Stack<>();
    private final Stack<String> deleted = new Stack<>();

    private static OutputPicker instance;

    private OutputPicker() {
        init();
    }

    private void init() {
        String create = getClass().getClassLoader().getResource("planetarium/resources/phrases/on_create").getFile();
        String delete = getClass().getClassLoader().getResource("planetarium/resources/phrases/on_delete").getFile();
        try (FileInputStream fr_c = new FileInputStream(new File(create));
                FileInputStream fr_d = new FileInputStream(new File(delete))) {
            created.addAll(Arrays.asList(new String(fr_c.readAllBytes()).split("\n")));
            deleted.addAll(Arrays.asList(new String(fr_d.readAllBytes()).split("\n")));
            Collections.shuffle(created);
            Collections.shuffle(deleted);
        } catch (IOException ioe) {
        }
    }

    public static OutputPicker getInstance() {
        if (instance == null) {
            instance = new OutputPicker();
        }
        return instance;
    }

    public String getOnDelete() {
        String s = deleted.pop();
        deleted.add(0, s);
        return s;
    }

    public String getOnCreate() {
        String s = created.pop();
        created.add(0, s);
        return s;
    }
}
