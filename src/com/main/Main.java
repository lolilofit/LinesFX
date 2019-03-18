package com.main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class Main {

    private static View view;

    public static void main(String[] args) throws IOException {

        Properties property = new Properties();
        property.load(Main.class.getResourceAsStream("resources/settings.properties"));
        Properties property_class = new Properties();
        property_class.load(Main.class.getResourceAsStream("resources/classes.properties"));
        view = Factory.getInstance().create_op_view(property_class.getProperty(property.getProperty("output_type")));

        Thread view_thread = new Thread(new Runnable() {
            public void run() {
                    ArrayList<Integer> f = new ArrayList<>();
                    for(int i = 0; i< 81; i++)
                        f.add(0);
                    //f.add(1);

                 //   view.show_current_field(f);
                    view.show_menu();
            }
        });

        view_thread.start();

        Thread game_thread = new Thread(new Runnable() {
            public void run() {
                Game game = null;
                try {
                    game = Game.getInstance(view);
                    game.game();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

       game_thread.start();

    }
}
