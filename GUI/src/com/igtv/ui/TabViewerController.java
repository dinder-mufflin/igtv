package com.igtv.ui;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.igtv.Main;
import com.igtv.structures.Frame;
import com.igtv.structures.Tablature;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TabViewerController extends AnchorPane implements Initializable {

  @FXML
  Line lnMarker;
  @FXML
  Button btnPlay;
  @FXML
  Pane anchorPane;
  @FXML
  Label lblTitle;

  private Tablature tabs;

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

    tabs = application.getTablature();

    lblTitle.setText(tabs.getTitle());

    LinkedList<Frame> frames = tabs.getFrames();

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

    for (int i = 0; i < notes.length; i++) {
      if (notes[i] == null) {
        continue;
      } else {
        double yOffset = yOffset(6 - i);
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
    Rectangle r = new Rectangle(10, boxHeight / 6, Color.LIGHTBLUE);
    r.relocate(xOffset, yOffset);

    Label l = new Label("" + fret);
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
    // Left margin
    int shift = 1;
    int pixelsPerBeat = 1;

    return (int) (shift + onset * pixelsPerBeat);
  }

  /**
   * Gets the y offset (in pixels) from a guitar string
   * 
   * @param guitarString String from the top
   * @return
   */
  public double yOffset(int guitarString) {
    // Top margin
    double shift = 0;
    double pixelsBetweenStrings = boxHeight / 6;

    return shift + pixelsBetweenStrings * guitarString;
  }

  private Timer scrollTimer;

  public void onPlayClicked(ActionEvent e) {
    if (application.player.isPlaying()) {
      stopTimer();
      application.player.stop();
    } else {
      application.player.stop();
      application.player.load(tabs.getScore().getSequence());
      application.player.play();

      final long offsetError = 0;

      scrollTimer = new Timer();
      scrollTimer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          lnMarker.setTranslateX(xOffset(application.player.getTickPosition() - offsetError));
        }
      }, 10, 10);
    }
  }
  
  public void stopTimer() {
    scrollTimer.cancel();
  }
}
