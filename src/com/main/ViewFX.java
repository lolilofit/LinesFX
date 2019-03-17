package com.main;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class ViewFX extends Application implements View {
//?????
  private int size;
  private static ArrayList<Integer> obs_list = null;
  private static ArrayList<ControllerFX> button_list = new ArrayList<>();
  private static IntegerProperty is_modified;

    @FXML
    private Button game;
    @FXML
    private Button exit;
    @FXML
    Label label;


 public ViewFX() {

     //считать с настроек
     size = 9;
     obs_list = new ArrayList<>(size*size);
     for(int i =0; i< size*size; i++)
         obs_list.add(0);
     obs_list.set(0, 1);

     is_modified = new SimpleIntegerProperty(0);
     is_modified.addListener(new InvalidationListener() {
         @Override
         public void invalidated(Observable observable) {
             Thread name = Thread.currentThread();
             if(name.getName() == "Thread-2") {

             }
         }
     });
 }

    @FXML
    private void game(ActionEvent actionEvent) {

    }

    @FXML
    private void exit(ActionEvent actionEvent) {

    }

   @Override
    public void start(Stage s) throws Exception {

       Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        s.setTitle("Lines");
        GridPane r = new GridPane();
        r.setPadding(new Insets(20));
        r.setHgap(20);
        r.setVgap(20);

       for (int k = 0; k < size * size; k++) {
           ControllerFX control = new ControllerFX(k, this, obs_list);
           control.get_button(obs_list.get(k).toString());
           button_list.add(k, control);
       }

           for(int i = 0 ; i<size; i++) {
            for (int j=0; j<size; j++) {
            r.add(button_list.get(i*size+j), j, i);
            }
        }

        VBox layout = new VBox(new Group(root), new Group(r));
        Scene sc = new Scene(layout, 300, 275);
        s.setScene(sc);
        s.show();

    }

    public void show_current_field(ArrayList<Integer> field) {

      for (int i = 0; i < size*size; i++) {
            obs_list.set(i, field.get(i));
 //           if(button_list.size() > i)
//           button_list.get(i).setText(field.get(i).toString());
      }

    }
    public void show_message(String message) {}
    public void show_game_stat() {}

    public void show_menu() {
        launch();
        }
    public void turn(String message) {

    }
}
