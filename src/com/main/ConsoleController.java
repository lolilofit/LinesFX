package com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleController implements ControlInterface{

    private ConsoleView view;

    public ConsoleController(ConsoleView _view) {
        view = _view;
    }

    public void get_message() {
        Scanner in = new Scanner(System.in); //wait
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
         }
        }
    }





