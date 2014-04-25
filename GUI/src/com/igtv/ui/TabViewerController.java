package com.igtv.ui;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TabViewerController extends AnchorPane implements Initializable {

  @FXML
  public Line lnMarker;
  @FXML
  private Button btnPlay;
  @FXML
  private Pane anchorPane;
  @FXML
  private Label lblTitle;

  private Tablature tabs;

  private double boxHeight, boxWidth;

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
    boxWidth = anchorPane.getWidth();

    tabs = application.getTablature();

    lblTitle.setText(tabs.getTitle());

    LinkedList<Frame> frames = tabs.getFrames();

    drawStringFiller();
    
    Iterator<Frame> i = frames.iterator();
    while (i.hasNext()) {
      // Our current frame
      Frame curr = i.next();
      drawFrame(curr);
    }
    
    lnMarker =  drawMarker();

    // Setup click event
    anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        lnMarker.setTranslateX(e.getX());
        lastPosition = (long) e.getX();
        application.player.seek((long) e.getX());
      }
    });
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
        drawNote(xOffset, yOffset, notes[i], frame.durations[i]);
      }
    }

    drawLines();
    //drawMeasureLines(tempo);
  }

  /**
   * Draws horizontal lines to divide strings
   */
  
  private void drawLines() {
    for (int i = 0; i < 6; i++) {
      double height = (boxHeight / 6) * i;
      Line l = new Line(0, height, anchorPane.getWidth(), height);
      l.setStroke(Color.BLACK);
      anchorPane.getChildren().add(l);
    }
  }
  
  /**
   * Draws vertical lines representing measures
   */
  private void drawMeasureLines(int tempo) {

  }
  
  /**
   * Fills in alternating colors for each string
   * 
   */
  private void drawStringFiller() {
    for(int i = 0; i <= 6; i++) {
      double yOffset = yOffset(6 - i);
      Rectangle r;
      int j = i % 2;
      if(j!=0){
        r = new Rectangle(boxWidth, boxHeight / 6, Color.LIGHTCYAN);
        r.relocate(0, yOffset);
      }
      else {
        r = new Rectangle(boxWidth, boxHeight / 6, Color.ALICEBLUE);
        r.relocate(0, yOffset);
      }
        anchorPane.getChildren().add(r);
    }
  }
  
  /**
   *Required to manually draw the line marker on-top of all other elements in pane 
   * 
   */
  private Line drawMarker() {
    Line l = new Line(0, 0, 0, boxHeight);
    l.setStroke(Color.CRIMSON);
    anchorPane.getChildren().add(l);
    return l;
  }

  // For disposal purposes
  public static ArrayList<Label> labelCache = new ArrayList<Label>();

  /**
   * Draws a note onto {@link #anchorPane}
   * 
   * @param xOffset
   * @param yOffset
   * @param fret
   * @param duration
   */
  private void drawNote(double xOffset, double yOffset, int fret, long duration) {
    Rectangle r = new Rectangle(duration, boxHeight / 6, Color.LIGHTBLUE);
    r.relocate(xOffset, yOffset);
    
    final Label l = new Label("" + fret);
    l.setFont(Font.font("Courier", 32));
    l.relocate(xOffset, yOffset);

    l.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("a note!");
        l.setTooltip(tooltip);
      }
    });

    labelCache.add(l);

    anchorPane.getChildren().addAll(r, l);
  }
  
  public int xOffset(double onset) {
    // Left margin
    int shift = 0;
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

  public Timer scrollTimer;
  private long lastPosition = 0;

  public void onPlayClicked(ActionEvent e) {
    if (application.player.isPlaying()) {
      btnPlay.setText("Play");
      stopTimer();
      lastPosition = application.player.getTickPosition();
      application.player.stop();
    } else {
      btnPlay.setText("Pause");
      application.player.stop();
      application.player.load(tabs.getScore().getSequence());
      application.player.seek(lastPosition);
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
    System.out.println(scrollTimer.hashCode());
    System.out.println(lnMarker.hashCode());
    scrollTimer.cancel();
  }
}
