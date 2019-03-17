package com.main;

import java.util.Scanner;

public class ConsoleController implements ControlInterface{
    Game game_game;

    public ConsoleController(int size) {

    }

    public void changes(int pos, int value) {}
    public String get_message() {
        Scanner in = new Scanner(System.in); //wait
        String operation = new String();
        while((operation= in.nextLine()).equals("")) {}
        return operation;
    //    game_game.current_in = operation;
    }

}



