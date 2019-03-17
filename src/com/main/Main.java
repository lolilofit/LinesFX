package com.main;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    private static ViewFX view = new ViewFX();

    public static void main(String[] args) {

        Thread view_thread = new Thread(new Runnable() {
            public void run() {
                    ArrayList<Integer> f = new ArrayList<>();
                    for(int i = 0; i< 80; i++)
                        f.add(0);
                    f.add(1);

                    view.show_current_field(f);
                    view.show_menu();
            }
        });

//        view_thread.setDaemon(true);


        view_thread.start();

        Thread game_thread = new Thread(new Runnable() {
            public void run() {
                Game game = null;
                try {
                    game = Game.getInstance(view);
                    game.menu();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

       game_thread.start();

    }
}
