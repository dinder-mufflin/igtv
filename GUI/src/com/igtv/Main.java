package com.igtv;

import java.io.File;
import java.io.InputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.igtv.audio.MidiPlayer;
import com.igtv.structures.Tablature;
import com.igtv.ui.ImportController;
import com.igtv.ui.TabViewerController;

/**
 * Class for driving entire UI
 */
public class Main extends Application {

  /**
   * Stage for displaying UI
   */
  private Stage stage;

  /**
   * Window minimum width
   */
  private final double MINIMUM_WINDOW_WIDTH = 390.0;

  /**
   * Window minimum height
   */
  private final double MINIMUM_WINDOW_HEIGHT = 500.0;

  /**
   * FileChooser used for choosing files
   */
  private final FileChooser fileChooser = new FileChooser();

  /**
   * Used for playing MIDI audio
   */
  public final MidiPlayer player = new MidiPlayer();

  /**
   * Tablature for display
   */
  public Tablature tabs;

  /**
   * Returns the tablature
   * 
   * @return The tablature
   */
  public Tablature getTablature() {
    return tabs;
  }

  @Override
  public void start(Stage primaryStage) {
    try {

      stage = primaryStage;
      stage.setTitle("IGTV");
      stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
      stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);

      gotoImport();

      primaryStage.show();
      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

        @Override
        public void handle(WindowEvent t) {
          Platform.exit();
          System.exit(0);
        }

      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Main method
   * 
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Prompts the user to pick a midi file
   * 
   * @return The file as a File object. If no file was chosen, null will be returned.
   */
  public File requestMidiFile() {
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
        new FileChooser.ExtensionFilter("MIDI", "*.mid"));

    return fileChooser.showOpenDialog(stage);
  }

  /**
   * Navigate to the import view
   */
  private void gotoImport() {
    try {
      ImportController importer = (ImportController) replaceSceneContent("ui/fxml/Import.fxml");
      importer.setApp(this);
    } catch (Exception e) {
      // An error occurred
      e.printStackTrace();
    }
  }

  /**
   * Navigate to the tablature viewer view
   * 
   * @param t
   */
  public void gotoTabViewer(Tablature t) {
    tabs = t;
    try {
      // Load from path
      TabViewerController viewer =
          (TabViewerController) replaceSceneContent("ui/fxml/TabViewer.fxml");

      // Gives reference to this object
      viewer.setApp(this);

      // Draws tablature on screen
      viewer.drawTablature();

    } catch (Exception e) {

      // An error occurred
      e.printStackTrace();
    }
  }

  /**
   * Replaces one scene with another
   * 
   * @param fxml
   * @return
   * @throws Exception
   */
  private Initializable replaceSceneContent(String fxml) throws Exception {

    // Create a loader
    FXMLLoader loader = new FXMLLoader();

    // Stream the fxml resource
    InputStream in = Main.class.getResourceAsStream(fxml);

    // Load the document
    loader.setBuilderFactory(new JavaFXBuilderFactory());
    loader.setLocation(Main.class.getResource(fxml));

    // Page loading
    AnchorPane page;

    // Instantiate the scene
    try {
      page = (AnchorPane) loader.load(in);
    } finally {
      in.close();
    }

    // Setup the scene
    Scene scene = new Scene(page, 800, 600);

    // Sets the scene to the one that was chosen
    stage.setScene(scene);

    // Resize
    stage.sizeToScene();

    // Returns the instantiated scene
    return (Initializable) loader.getController();
  }
}
