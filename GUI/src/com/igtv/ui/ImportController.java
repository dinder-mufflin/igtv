package com.igtv.ui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import com.igtv.Main;
import com.igtv.midi.io.MidiReader;
import com.igtv.structures.Note;
import com.igtv.structures.Score;
import com.igtv.structures.Tablature;

/**
 * Contains logic and variables for the first two tabs of the IGTV program. Allows users to input,
 * preview and create tablature from various MIDI files.
 * 
 */
public class ImportController extends AnchorPane implements Initializable {

  @FXML
  private Button btnImport;
  @FXML
  private Button btnPreview;
  @FXML
  private Button btnTrackSubmit;
  @FXML
  private TextField txtImport;
  @FXML
  private TabPane tabPane;
  @FXML
  private Tab tabTrack;
  @FXML
  private TableView<TrackTableItem> tblTracks;
  @FXML
  private TableColumn colNumber;
  @FXML
  private TableColumn colInstrument;

  /**
   * Application being run
   */
  private Main application;

  /**
   * Name of the file being imported
   */
  private String fileName;

  /**
   * Sets the {@link Main} application
   * 
   * @param application Current running application
   */
  public void setApp(Main application) {
    this.application = application;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // This is basically our constructor
  }

  /**
   * Called when the import button is clicked. Prompts the user for a .mid file and then asks the
   * user to choose a track.
   * 
   * @pre none
   * @post Table of tracks is created
   * 
   * @param event Data relevant to the click event
   */
  public void onImportClicked(ActionEvent event) {
    // Prompts the user to pick a MIDI file
    File file = application.requestMidiFile();

    if (file == null) {
      // User hit cancel
    } else {
      // User chose successfully
      txtImport.setText(file.getName());
      fileName = file.getName();
      btnImport.setDisable(true);

      tabTrack.setDisable(false);

      colInstrument.setCellValueFactory(new PropertyValueFactory<TrackTableItem, String>(
          "instrument"));
      colNumber.setCellValueFactory(new PropertyValueFactory<TrackTableItem, Integer>("number"));

      Score importedScore = MidiReader.readScore(file.getPath());

      ObservableList<TrackTableItem> tracks = FXCollections.observableArrayList();

      // Add full score
      tracks.add(new TrackTableItem(0, "All Tracks", importedScore));

      // Add individual tracks
      for (int i = 1; i < importedScore.numberOfTracks(); i++) {

        Score currentTrack = importedScore.getTrack(i);

        tracks.add(new TrackTableItem(i, "TEST", currentTrack));
      }

      tblTracks.setItems(tracks);
    }
  }

  /**
   * 
   * TrackTableItem provides a place holder for all of the track table data. This includes the track
   * number, track instrument and the score that it references.
   * 
   */
  public static class TrackTableItem {
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty instrument;
    private final Score score;

    /**
     * Main constructor
     * 
     * @param number Number of the track
     * @param instrument Name of the instrument
     * @param score Referenced score
     */
    private TrackTableItem(int number, String instrument, Score score) {
      this.number = new SimpleIntegerProperty(number);
      this.instrument = new SimpleStringProperty(instrument);
      this.score = score;
    }

    // GETTERS AND SETTERS //
    public int getNumber() {
      return number.get();
    }

    public String getInstrument() {
      return instrument.get();
    }

    public Score getScore() {
      return score;
    }
  }

  /**
   * Changes the parameters of the buttons and labels depending on what track is currently selected.
   * It track 0 (All Instruments) track is selected then user will not have the ability to process
   * the track into tablature. Only the option to preview all instruments will be available.
   * 
   * @pre User has a selected track
   * @post Correct options will be available depending on what track is selected
   * 
   * @param e
   */
  public void onTrackSelected(Event e) {
    if (tblTracks.getSelectionModel().getSelectedItem().getNumber() == 0) {
      // Make it so user can't create tablature
      btnPreview.setOpacity(1);
      btnTrackSubmit.setOpacity(.5);
      btnPreview.setDisable(false);
      btnTrackSubmit.setDisable(true);
    } else {
      // All features are available
      btnPreview.setOpacity(1);
      btnTrackSubmit.setOpacity(1);
      btnPreview.setDisable(false);
      btnTrackSubmit.setDisable(false);
    }
  }

  /**
   * Logic for when a user hits the 'Preview' button.
   * 
   * @pre Users has a track selected
   * @post Score will start to play
   * 
   * @param e
   */
  public void onPreviewRequested(ActionEvent e) {
    if (application.player.isPlaying()) {
      application.player.stop();
      // Display "Preview" in the button
      btnPreview.setText("Preview");
    } else {
      // Select correct track
      TrackTableItem item = tblTracks.getSelectionModel().getSelectedItem();

      // Create the score from the track
      Score score = item.getScore();

      // Find the start of the first note and start playing from that point. This solves any issues
      // where tracks start late in songs.
      application.player.load(score.getSequence());
      long start = score.getNotes().get(0).getOnsetInTicks();
      System.out.println("Start = " + start);
      application.player.seek(start);
      application.player.play();

      // Display "Stop" in the button
      btnPreview.setText("Stop");
    }

  }

  /**
   * Logic for when user hits 'Submit' button. Creates the tablature from the selected track.
   * 
   * @pre User selects a valid track
   * @post {@link Tablature} item is created
   * 
   * @param
   */
  public void onTrackSubmit(ActionEvent e) {
    // Validate their choice
    if (tblTracks.getSelectionModel().getSelectedIndex() == 0) {
      // Do nothing. They chose an option that doesn't make sense
      return;
    }

    // Get the selected item
    TrackTableItem item = tblTracks.getSelectionModel().getSelectedItem();

    // Create a score and create the tablature from that score
    Score score = item.getScore();
    Tablature t = new Tablature(score);
    String[] noExtFileName = fileName.split("\\.");
    t.setTitle(noExtFileName[0]);

    // Switch view to TabViewer
    application.gotoTabViewer(t);
  }
}
