package com.main;

import java.util.ArrayList;

public interface View // extends Thread {
{
    void show_current_field(ArrayList<Integer> field);
    void show_game_stat(int _score);
    void show_menu();
    void help();
    void exit();
}
