package com.igtv;

import java.io.File;
import java.io.InputStream;

import com.igtv.ui.LoadController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

  private Stage stage;

  private final double MINIMUM_WINDOW_WIDTH = 390.0;
  private final double MINIMUM_WINDOW_HEIGHT = 500.0;

  private final FileChooser fileChooser = new FileChooser();

  @Override
  public void start(Stage primaryStage) {
    try {
      
      stage = primaryStage;
      stage.setTitle("FXML Login Sample");
      stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
      stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
      
      gotoLoad();
      
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
  
  public File requestMidiFile() {
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("All Images", "*.*"),
        new FileChooser.ExtensionFilter("MIDI", "*.mid")
    );
    
    return fileChooser.showOpenDialog(stage);
  }
  
  private void gotoLoad() {
    try {
      LoadController load = (LoadController) replaceSceneContent("ui/fxml/Load.fxml");
      load.setApp(this);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private Initializable replaceSceneContent(String fxml) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    InputStream in = Main.class.getResourceAsStream(fxml);
    loader.setBuilderFactory(new JavaFXBuilderFactory());
    loader.setLocation(Main.class.getResource(fxml));
    AnchorPane page;
    try {
        page = (AnchorPane) loader.load(in);
    } finally {
        in.close();
    } 
    Scene scene = new Scene(page, 800, 600);
    stage.setScene(scene);
    stage.sizeToScene();
    return (Initializable) loader.getController();
}
}