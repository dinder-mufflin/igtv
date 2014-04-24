package com.igtv.ui;

import java.awt.ScrollPane;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.igtv.Main;
import com.igtv.structures.Frame;
import com.igtv.structures.Note;
import com.igtv.structures.Score;
import com.igtv.structures.Tablature;
import com.igtv.midi.io.MidiReader;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TabViewerController extends AnchorPane implements Initializable {

  @FXML
  Button btnPlay;
  @FXML
  Pane anchorPane;
  @FXML
  Label lblTitle;
  
  private double boxHeight;

  private Main application;

  public void setApp(Main application) {
    this.application = application;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    
  }

  /**
   * Draws the entire tablature onto {@link #anchorPane}
   */
  public void drawTablature() {
    boxHeight = anchorPane.getHeight();
    
    Tablature t = application.getTablature();

    LinkedList<Frame> frames = t.getFrames();

    Iterator<Frame> i = frames.iterator();
    while (i.hasNext()) {
      // Our current frame
      Frame curr = i.next();
      drawFrame(curr);
    }
  }

  /**
   * Draws one frame onto {@link #anchorPane}
   * 
   * @param frame
   */
  private void drawFrame(Frame frame) {
    int xOffset = xOffset(frame.getOnsetInTicks());
    
    Integer[] notes = frame.guitarStringFrets;
    
    for(int i=0; i<notes.length; i++) {
      if(notes[i] == null) {
        continue;
      } else {
        double yOffset = yOffset(6-i);
        drawNote(xOffset, yOffset, notes[i], 10);
      }
    }
  }

  /**
   * Draws a note onto {@link #anchorPane}
   * 
   * @param xOffset
   * @param yOffset
   * @param fret
   * @param duration
   */
  private void drawNote(double xOffset, double yOffset, int fret, long duration) {
    Rectangle r = new Rectangle(10, boxHeight/6, Color.LIGHTBLUE);
    r.relocate(xOffset, yOffset);
    
    Label l = new Label(""+fret);
    l.relocate(xOffset, yOffset);
    
    anchorPane.getChildren().addAll(r, l);
    
  }

  /**
   * Gets the x offset (in pixels) from an onset
   * 
   * @param onset
   * @return
   */
  public int xOffset(double onset) {
//    return counter++;
    
    // Left margin
    int shift = 1;
    int pixelsPerBeat = 1;

    return (int) (shift + onset * pixelsPerBeat);
  }
  
  //private int counter = 0; //TODO: Remove

  /**
   * Gets the y offset (in pixels) from a guitar string
   * 
   * @param guitarString String from the top
   * @return
   */
  public double yOffset(int guitarString) {
    // Top margin
    double shift = 0;
    double pixelsBetweenStrings = boxHeight/6;

    return shift + pixelsBetweenStrings * guitarString;
  }

  public void onPlayClicked(ActionEvent e) {
    // TODO:
  }
}
