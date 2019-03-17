package com.main;

import java.io.IOException;
import java.util.Properties;

public class Game {
    Board game_field;
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

    public void game() {
        view.show_current_field(game_field.getField());
        view.show_game_stat(game_field.get_score());

        boolean was_set = true;
        view.show_game_stat(game_field.get_score());
        try {
            synchronized (Game.getInstance((ViewFX) view)) {
                (Game.getInstance((ViewFX) view)).wait();
            }
            String n = controller_message;
            String[] mes = n.split(" ");
            was_set = game_field.player_turn(Integer.parseInt(mes[0]), Integer.parseInt(mes[1]));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        view.show_game_stat(game_field.get_score());

        while (game_field.is_space_enough()) {
            try {
                if (was_set)
                    game_field.computer_turn();


                view.show_current_field(game_field.getField());
                synchronized (Game.getInstance((ViewFX) view)) {
                    (Game.getInstance((ViewFX) view)).wait();
                    }
                    String n = controller_message;
                 String[] mes = n.split(" ");
                 was_set = game_field.player_turn(Integer.parseInt(mes[0]), Integer.parseInt(mes[1]));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }

            view.show_game_stat(game_field.get_score());
        }

    }

}
