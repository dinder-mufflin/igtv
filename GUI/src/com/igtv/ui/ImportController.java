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

  /**
   * Import button
   */
  @FXML
  private Button btnImport;

  /**
   * Audio preview button
   */
  @FXML
  private Button btnPreview;

  /**
   * Track Submit button
   */
  @FXML
  private Button btnTrackSubmit;

  /**
   * Import text field (used for shoing the path)
   */
  @FXML
  private TextField txtImport;

  /**
   * Pane for displaying multiple views
   */
  @FXML
  private TabPane tabPane;

  /**
   * Track tab in {@link #tabPane}
   */
  @FXML
  private Tab tabTrack;

  /**
   * Table of tracks for selection
   */
  @FXML
  private TableView<TrackTableItem> tblTracks;

  /**
   * Column for {@link #tblTracks}
   */
  @FXML
  private TableColumn colNumber;

  /**
   * Instrument column for {@link #tblTracks}
   */
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

    //Check if the user selected a file
    if (file == null) {
      
      // User hit cancel
    } else {
      
      // User chose successfully
      txtImport.setText(file.getName());
      fileName = file.getName();
      
      //Disable the import button
      btnImport.setDisable(true);

      //Enable the tab track
      tabTrack.setDisable(false);

      //Create factories for displaying in the table
      colInstrument.setCellValueFactory(new PropertyValueFactory<TrackTableItem, String>(
          "instrument"));
      colNumber.setCellValueFactory(new PropertyValueFactory<TrackTableItem, Integer>("number"));

      //Gets the chosen track (as a Score)
      Score importedScore = MidiReader.readScore(file.getPath());

      ObservableList<TrackTableItem> tracks = FXCollections.observableArrayList();

      // Add full score
      tracks.add(new TrackTableItem(0, "All Tracks", importedScore));

      // Add individual tracks
      for (int i = 1; i < importedScore.numberOfTracks(); i++) {

        Score currentTrack = importedScore.getTrack(i);

        tracks.add(new TrackTableItem(i, "", currentTrack));
      }

      //Add to the UI
      tblTracks.setItems(tracks);
    }
  }

  /**
   * TrackTableItem provides a place holder for all of the track table data. This includes the track
   * number, track instrument and the score that it references.
   * 
   * @see TableView
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

    /**
     * Returns the number of the track
     * @return Track number
     */
    public int getNumber() {
      return number.get();
    }

    /**
     * Returns the instrument of the track
     * @return Track instrument
     */
    public String getInstrument() {
      return instrument.get();
    }

    /**
     * Returns the score of the track
     * @return
     */
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
   * @param e Click event
   */
  public void onTrackSelected(Event e) {
    
    //Check if the user clicked the first track (play all)
    if (tblTracks.getSelectionModel().getSelectedItem().getNumber() == 0) {
      
      // Disable selection of this item
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

    application.player.stop();
    
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
