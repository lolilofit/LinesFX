package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Game {
    private Board game_field;
    private View view;
    private static volatile Game instance;
    private String controller_message;

    private Game(View _view) throws  IOException{

        Properties property = new Properties();
        property.load(getClass().getResourceAsStream("resources/settings.properties"));

        int size = Integer.parseInt(property.getProperty("field_size"));
        game_field = new Board();
        view = _view;
  }

    public static Game getInstance(View _view) throws IOException {

        if (instance == null) {
            synchronized (Factory.class) {
                if (instance == null) {
                    instance = new Game(_view);
                }
            }
        }
        return instance;
    }

    public void  setMessage(String message) {
        controller_message = message;
    }

    public ArrayList<Integer> return_field() {
        return game_field.getField();
    }

    public void game() {

        boolean was_set = true;

        try {
            view.show_game_stat(game_field.get_score());

            while (game_field.is_space_enough()) {
                if (was_set)
                    game_field.computer_turn();

                synchronized (Game.getInstance(view)) {
                    (Game.getInstance(view)).wait();
                }

                String n = controller_message;
                if (controller_message == "help") {
                    view.help();
                } else if (controller_message == "exit") {
                    view.exit();
                } else {
                    String[] mes = n.split(" ");
                    was_set = game_field.player_turn(Integer.parseInt(mes[0]), Integer.parseInt(mes[1]));
                }

                view.show_game_stat(game_field.get_score());
            }
        }
        catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

        }

    }


