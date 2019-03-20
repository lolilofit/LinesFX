package com.main.IO_realisations;

import com.main.ControlInterface;
import com.main.Game;

import java.util.Scanner;

public class ConsoleController implements ControlInterface {

    private ConsoleView view;

    public ConsoleController(ConsoleView _view) {
        view = _view;
    }

    public void get_message() {
        Scanner in = new Scanner(System.in);
        String operation = new String();

        try {
            synchronized (Game.getInstance(view)) {
                (Game.getInstance(view)).notifyAll();
            }

            while (true) {
                if ((operation = in.nextLine()).equals("") == false) {
                    synchronized (Game.getInstance(view)) {
                        Game.getInstance(view).setMessage(operation);
                        (Game.getInstance(view)).notifyAll();
                    }
                }
            }
          }catch (Exception e) {
             e.printStackTrace();
             return;
         }
        }
    }





