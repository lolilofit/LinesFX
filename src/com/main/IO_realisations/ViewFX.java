package com.main.IO_realisations;

import com.main.Game;
import com.main.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ViewFX extends Application implements View {

  private int size;
  private ArrayList<Integer> obs_list = null;
  private static Integer score = 0;
  private Integer colour_num;

 public ViewFX() {

        try {
            Properties property = new Properties();
            property.load(getClass().getResourceAsStream("../resources/settings.properties"));
            size = Integer.parseInt(property.getProperty("field_size"));
            colour_num = Integer.parseInt(property.getProperty("colour_num"));

            obs_list = new ArrayList<>(size*size);
            obs_list = Game.getInstance(this).return_field();

        } catch (IOException e) {
         e.printStackTrace();
         Platform.exit();
         System.exit(1);
     }
 }

    public void exit(javafx.event.ActionEvent actionEvent) {
        exit();
    }

    @Override
    public void start(Stage s) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        GridPane r = new GridPane();
        s.setTitle("Lines");
        r.setPadding(new Insets(20));
        r.setHgap(20);
        r.setVgap(20);


           for(int i = 0 ; i<size; i++) {
            for (int j=0; j<size; j++) {
                ControllerFX control = new ControllerFX(i*size+j, this, colour_num);
                control.get_button();
                r.add(control, j + 1, i+ 2);
            }
           }

          Button score_button = new Button("Score is: 0");
     //   BackgroundFill test = new BackgroundFill(new Paint() {

      //  }, null, null);
         score_button.setStyle("-fx-background-color: #ba50ff; -fx-animated: fly");

          score_button.setOnAction(event -> score_button.setText("Score is: " + score.toString()));

          r.add(score_button,1, 0, size, 1);

        VBox layout = new VBox(new Group(root), new Group(r));

        Scene sc = new Scene(layout, 600, 660);
        s.setScene(sc);
        s.show();

    }

    public void show_current_field(ArrayList<Integer> field) {

      for (int i = 0; i < size*size; i++)
            obs_list.set(i, field.get(i));
    }

    public ArrayList<Integer> get_updated_field() {
     return obs_list;
    }

    public void show_game_stat(int _score) {
       score = _score;
    }

    public void show_menu() {
        launch();
    }

    public void help() {}

    public void exit() {
        Platform.exit();
        System.exit(0);
    }
}
