package com.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerFX extends Button implements ControlInterface, ButtonFactory {

    private static Integer last_pressed = 0, prev_pos = -1, begin = 0, end = 0;
    private String button_text;
    public int number;
    private ViewFX _view;
    private static ArrayList<ControllerFX> button_list = new ArrayList<>();

    public ControllerFX(int _number) {
        button_text = "";
        number = _number;
    }

    public ControllerFX(int _number, ViewFX view, int colour_num) {
        button_text = "";
        number = _number;
        _view = view;
    }


    public void  action(ActionEvent e) throws IOException {

                if (Integer.parseInt(button_text) != 0) {
                    prev_pos = Integer.parseInt(button_text);
                    begin = number;
                    this.button_text = "0";
                }
                else if (prev_pos != -1 && (Integer.parseInt(button_text) == 0)) {
                     end = number;
                     prev_pos = -1;


                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(50);
                            ArrayList<Integer> field = _view.get_updated_field();
                            for (int i = 0; i < button_list.size(); i++) {
                                //button_list.get(i).setText(field.get(i).toString());
                                button_list.get(i).button_text = field.get(i).toString();

                                Image image  = new Image(getClass().getResourceAsStream( "circles_png/" + field.get(i).toString() + ".png"));
                                ImageView imageView = new ImageView(image);
                                 button_list.get(i).setGraphic(imageView);
                                image = null;
                                imageView = null;

                            }
                        }
                        catch (Exception ex) {
                         ex.printStackTrace();
                        }
                    });

                    synchronized (Game.getInstance(_view)) {
                         get_message();
                        (Game.getInstance(_view)).notifyAll();
                    }

                }
   }

   public Button get_button()  {

       ArrayList<Integer> field = _view.get_updated_field();
       button_text = field.get(number).toString();

           Image image = new Image(getClass().getResourceAsStream("circles_png/" + button_text + ".png"));
           ImageView imageView = new ImageView(image);
           this.setStyle("-fx-background-color: 255.255.255.0");
           this.setGraphic(imageView);
           image = null;
           imageView = null;


        this.setOnAction(e -> {
            try {
                action(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        button_list.add(number, this);
        return this;
    }

    public void get_message() {
        try {
            Game.getInstance(_view).setMessage(begin.toString() + " " + end.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
