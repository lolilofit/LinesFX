package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import static java.lang.Integer.*;

public class Game {
    Board game_field;
    private static View view;
    private ControlInterface control;
    private static volatile Game instance;
    private static String controller_message;

    private Game(ViewFX _view) throws  IOException{

        Properties property = new Properties();
        property.load(getClass().getResourceAsStream("resources/settings.properties"));
        Properties property_class = new Properties();
        property_class.load(getClass().getResourceAsStream("resources/classes.properties"));
        int size = Integer.parseInt(property.getProperty("field_size"));
        game_field = new Board();
        //view = Factory.getInstance().create_op_view(property_class.getProperty(property.getProperty("output_type")));


        view = _view;
    }

    public static Game getInstance(ViewFX _view) throws IOException {

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

    public void menu() {


        view.show_current_field(game_field.getField());

        ControllerFX _control = new ControllerFX(82 , (ViewFX) view, game_field.getField());

        try {
            synchronized (Game.getInstance((ViewFX) view)) {
                (Game.getInstance((ViewFX) view)).wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String _n = controller_message;
        String[] _mes = _n.split(" ");
        game_field.player_turn(Integer.parseInt(_mes[0]), Integer.parseInt(_mes[1]));

        view.show_current_field(game_field.getField());


        //if(current_in.equals("Game"))

            game();
        //if(current_in.equals("Exit"))
         //   return;
    }

    private void game() {
        boolean was_set = true;
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
        }
    }

}
