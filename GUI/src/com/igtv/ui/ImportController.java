package com.igtv.ui;

import java.io.File;
import java.net.URL;
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
import com.igtv.structures.Score;
import com.igtv.structures.Tablature;

public class ImportController extends AnchorPane implements Initializable {
//here is a test comment for a commit: Elliot
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

  private Main application;

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
      btnImport.setDisable(true);

      tabTrack.setDisable(false);

      colInstrument.setCellValueFactory(new PropertyValueFactory<TrackTableItem, String>(
          "instrument"));
      colNumber.setCellValueFactory(new PropertyValueFactory<TrackTableItem, Integer>("number"));

      Score importedScore = MidiReader.readScore(file.getPath());

      ObservableList<TrackTableItem> tracks = FXCollections.observableArrayList();

      // Add full score
      tracks.add(new TrackTableItem(0, "All", importedScore));

      // Add individual tracks
      for (int i = 1; i < importedScore.numberOfTracks(); i++) {

        Score currentTrack = importedScore.getTrack(i);

        tracks.add(new TrackTableItem(i, "empty", currentTrack));

      }

      tblTracks.setItems(tracks);
    }
  }

  public static class TrackTableItem {
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty instrument;
    private final Score score;

    private TrackTableItem(int number, String instrument, Score score) {
      this.number = new SimpleIntegerProperty(number);
      this.instrument = new SimpleStringProperty(instrument);
      this.score = score;
    }

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

  public void onTrackSelected(Event e) {
    btnPreview.setDisable(false);
    btnTrackSubmit.setDisable(false);
  }

  public void onPreviewRequested(ActionEvent e) {
    if (application.player.isPlaying()) {
      application.player.stop();
      // Display "Preview" in the button
      btnPreview.setText("Preview");
    } else {
      TrackTableItem item = (TrackTableItem) tblTracks.getSelectionModel().getSelectedItem();

      Score score = item.getScore();

      application.player.load(score.getSequence());
      long start = score.getNotes().get(0).getOnsetInTicks();
      System.out.println("Start = " + start);
      application.player.seek(start);
      application.player.play();

      // Display "Stop" in the button
      btnPreview.setText("Stop");
    }

  }

  public void onTrackSubmit(ActionEvent e) {
    // Validate their choice
    if (tblTracks.getSelectionModel().getSelectedIndex() == 0) {
      // Do nothing. They chose an option that doesn't make sense
      return;
    }

    TrackTableItem item = (TrackTableItem) tblTracks.getSelectionModel().getSelectedItem();

    Score score = item.getScore();
    Tablature t = new Tablature(score);
    t.setTitle("Hotel California"); // TODO: Replace with file's title

    application.gotoTabViewer(t);
  }
}
