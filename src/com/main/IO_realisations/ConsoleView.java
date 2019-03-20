package com.main.IO_realisations;

import com.main.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class ConsoleView implements View {

    private ConsoleController control;
    private int size;

    public ConsoleView() {
        try {
            Properties property = new Properties();
            property.load(getClass().getResourceAsStream("../resources/settings.properties"));
            size = Integer.parseInt(property.getProperty("field_size"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        control = new ConsoleController(this);
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

        System.out.println("Choose one of:\nAbout\nMake Turn\nExit");
        control.get_message();
    }

    @Override
    public void help() {
        System.out.println("Move the balls from cell to cell to group them into the lines of the same color.\n"
                + "(Type the number of cell you want to move and number of Destination)");
    }

    @Override
    public void exit() {
         System.exit(0);
    }

    public void show_game_stat(int _score) {
      System.out.println("Score is: " + _score);
    }
}
