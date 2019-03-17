package com.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerFX extends Button implements ControlInterface, ButtonFactory {

    private static Integer last_pressed = 0, prev_pos = -1, begin, end;
    private String button_text;
    public int number;
    private ViewFX _view;
    private ArrayList<Integer> field;

    public ControllerFX(int _number, ViewFX view, ArrayList<Integer> _field) {
        button_text = "";
        number = _number;
        _view = view;
        field = _field;
    }


    public void  action(ActionEvent e) throws IOException {

                if (Integer.parseInt(button_text) != 0) {
                    prev_pos = Integer.parseInt(button_text);
                    begin = number;
                    button_text = "0";
                    this.setText("0");
                }
                else if (prev_pos != 0 && (Integer.parseInt(button_text) == 0)) {
                   // game_message = prev_pos.toString() + " " + last_pressed.toString();
                    end = number;


                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(1000);
                            this.setText(prev_pos.toString());
                        }
                        catch (Exception ex) {
                         ex.printStackTrace();
                        }
                    });

                    synchronized (Game.getInstance(_view)) {
                        Game.getInstance(_view).setMessage(begin.toString() + " " + end.toString());
                        (Game.getInstance(_view)).notifyAll();
                    }

                   // this.setText(prev_pos.toString());
                   // prev_pos = -1;
                }
   }

   public Button get_button(String text) {
        button_text = text;
        this.setText(button_text);
        this.setOnAction(e -> {
            try {
                action(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        return this;
    }

    public String get_message() {
        return prev_pos.toString() + " " + last_pressed.toString();
    }

}
