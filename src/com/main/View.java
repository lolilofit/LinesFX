package com.main;

import java.io.IOException;
import java.util.ArrayList;

public interface View // extends Thread {
{
    void show_current_field(ArrayList<Integer> field);
    void show_message(String message);
    void show_game_stat();
    void show_menu();
    void turn(String message);
}
