package com.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerFX extends Button implements ControlInterface, ButtonFactory {

    private static Integer last_pressed = 0, prev_pos = -1, begin, end;
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

/*
        for(Integer i=0; i< colour_num; i++) {
            Image image = new Image(getClass().getResourceAsStream("png_files/" + i.toString() + ".png"));
            balls.put(i, image);
        }
        
      //  Image image = new Image(getClass().getResourceAsStream("png_files/" + button_text + ".png"));
*/
    }


    public void  action(ActionEvent e) throws IOException {

                if (Integer.parseInt(button_text) != 0) {
                    prev_pos = Integer.parseInt(button_text);
                    begin = number;
                    this.button_text = "0";
                }
                else if (prev_pos != -1 && (Integer.parseInt(button_text) == 0)) {
                   // game_message = prev_pos.toString() + " " + last_pressed.toString();
                    end = number;
                     prev_pos = -1;


                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(800);
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
                        Game.getInstance(_view).setMessage(begin.toString() + " " + end.toString());
                        (Game.getInstance(_view)).notifyAll();
                    }

                }
   }

   public Button get_button()  {

       ArrayList<Integer> field = _view.get_updated_field();
       button_text = field.get(number).toString();
   //    this.setText(button_text);

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

    public String get_message() {
        return prev_pos.toString() + " " + last_pressed.toString();
    }

}
