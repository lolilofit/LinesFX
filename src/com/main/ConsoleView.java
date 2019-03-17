package com.main;

import java.util.ArrayList;


public class ConsoleView implements View {

    private ControlInterface control;
    private int size;

    private static ArrayList<Integer> obs_list;

    public ConsoleView(int _size) {
        size = _size;
        control = new ConsoleController(size);
    }

    public void show_current_field(ArrayList<Integer> field)   {

        String line = "";
        for(int i =0; i<size; i++) {
            for (int j =0; j<size; j++) {
                line += field.get(i*size+j) + " ";
            }
            System.out.println(line + "\n");
            line = "";
        }
    }

    public void show_menu() {

        System.out.println("Choose one of:\nAbout\nGame\nExit");
       // control.get_string();
    }

    public void show_game_stat(int _score) {

    }
}
