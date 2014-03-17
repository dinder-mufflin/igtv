package com.igtv.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.igtv.Main;
import com.igtv.audio.MidiPlayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoadController extends AnchorPane implements Initializable {

  @FXML
  private TextField path;
  @FXML
  private Button submit;
  @FXML
  private Label errorMessage;

  private Main application;

  public void setApp(Main application) {
    this.application = application;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    errorMessage.setText("");
    path.setPromptText("demo");

  }

  public void processLoad(ActionEvent event) {
    if (application == null) {
      errorMessage.setText("Something went horribly wrong!");
    } else {
      File file = application.requestMidiFile();
      if (file == null) {
        errorMessage.setText("Didn't work!");
      } else {
        MidiPlayer player = new MidiPlayer();

        player.load(file);
        player.play();


        // errorMessage.setText("Success!");
      }
    }
  }
}
