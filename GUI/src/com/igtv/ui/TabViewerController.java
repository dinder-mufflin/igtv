package com.igtv.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.igtv.Main;
import com.igtv.structures.Frame;
import com.igtv.structures.Tablature;

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
import javafx.scene.shape.Rectangle;

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

  // Used for the main section where the tablature will be viewed
  private double boxHeight, boxWidth;

  // Main variable
  private Main application;

  /**
   * Sets the Main application object
   * @param application
   */
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
    // Set the box width and height
    boxHeight = anchorPane.getHeight();
    boxWidth = anchorPane.getWidth();

    tabs = application.getTablature();

    // Sets the main label from import file
    lblTitle.setText(tabs.getTitle());

    LinkedList<Frame> frames = tabs.getFrames();

    // drawStringFiller();
    drawTablatureLines();

    Iterator<Frame> i = frames.iterator();
    while (i.hasNext()) {
      // Our current frame
      Frame curr = i.next();
      drawFrame(curr);
    }

    // Create measure lines so learner can better see what notes are closely related
    drawMeasureLines();

    lnMarker = drawMarker();

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

    // Create the notes and draw them onto the tablature
    for (int i = 0; i < notes.length; i++) {
      if (notes[i] == null) {
        continue;
      } else {
        double yOffset = yOffset(6 - i);
        drawNote(xOffset, yOffset, notes[i], frame.durations[i]);
      }
    }
  }

  /**
   * Draws horizontal tab lines, populating from the middle onto {@link #anchorPane}
   * 
   * 
   */
  private void drawTablatureLines() {
    int i, j, k;
    int tabRatioY = (int) (boxHeight / 6);
    int tabBorderX = (int) (boxWidth);
    int centerY = (int) (boxHeight / 2);

    Line l1;
    Line l2;
    for (i = 0, j = centerY - tabRatioY / 2, k = centerY + tabRatioY / 2; i < 6 / 2; i++, j -=
        tabRatioY, k += tabRatioY) {
      l1 = new Line(tabBorderX, j, boxWidth - tabBorderX, j);
      l2 = new Line(tabBorderX, k, boxWidth - tabBorderX, k);
      l1.setStroke(Color.BLACK);
      l2.setStroke(Color.BLACK);
      anchorPane.getChildren().addAll(l1, l2);
    }

  }

  /**
   * Draws vertical lines to represent measures in standard 4/4 time propagating from the middle of
   * {@link #anchorPane}
   * 
   */
  public void drawMeasureLines() {
    Line l;
    Rectangle r;
    int measureLine = tabs.getMeasure();
    int tempMeasureLine = 0;
    for (int i = 0; i < boxWidth; i++) {
      l = new Line(tempMeasureLine, 0, tempMeasureLine, boxHeight);
      anchorPane.getChildren().addAll(l);
      tempMeasureLine += measureLine;
    }
  }

  /**
   * Fills in alternating colors at each string onto {@link #anchorPane}
   * 
   */
  private void drawStringFiller() {
    for (int i = 0; i <= 6; i++) {
      double yOffset = yOffset((6 - i));
      Rectangle r;
      int j = i % 2;
      if (j != 0) {
        r = new Rectangle(boxWidth, boxHeight / 6, Color.LIGHTCYAN);
        r.relocate(0, yOffset);
      } else {
        r = new Rectangle(boxWidth, boxHeight / 6, Color.ALICEBLUE);
        r.relocate(0, yOffset);
      }
      anchorPane.getChildren().add(r);
    }
  }

  /**
   * Required to manually draw the line marker on-top of all other elements in pane
   * 
   */
  private Line drawMarker() {
    Line l = new Line(0, 0, 0, boxHeight);
    l.setStroke(Color.RED);
    anchorPane.getChildren().add(l);
    return l;
  }

  // For disposal purposes
  public static ArrayList<Label> labelCache = new ArrayList<Label>();

  /**
   * Draws a note onto {@link #anchorPane}
   * 
   * @param xOffset The positioning (in pixels) the note will be drawn
   * @param yOffset The positioning (in pixels) the note will be drawn
   * @param fret The guitar fret that the note is going to be played
   * @param duration How long the note will be held for
   */
  private void drawNote(double xOffset, double yOffset, int fret, long duration) {

    Rectangle r = new Rectangle(duration, boxHeight / 6, noteColor(fret));

    // Used to add beautification to the notes
    r.setArcWidth(25);
    r.setArcHeight(25);
    r.setStrokeWidth(1.0);
    r.setStroke(Color.BLACK);

    r.relocate(xOffset, yOffset);

    // The Label will display the fret value
    final Label l = new Label("" + fret);

    // for centering and displaying the fret number
    double labelXValue = xOffset + 10.0;
    double labelYValue = yOffset + (boxHeight / 12) - 6;

    l.relocate(labelXValue, labelYValue);

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

  /**
   * Returns a color value of the note. This aids in visually cueing the player which notes are
   * coming up.
   * 
   * @param fret
   * @return
   */
  private Color noteColor(int fret) {
    Color fretColor;
    switch (fret % 12) {
      case 1:
        fretColor = Color.CORAL;
        break;
      case 2:
        fretColor = Color.SALMON;
        break;
      case 3:
        fretColor = Color.BISQUE;
        break;
      case 4:
        fretColor = Color.GOLD;
        break;
      case 5:
        fretColor = Color.PALEGOLDENROD;
        break;
      case 6:
        fretColor = Color.LIGHTGREEN;
        break;
      case 7:
        fretColor = Color.LIGHTSEAGREEN;
        break;
      case 8:
        fretColor = Color.LIGHTBLUE;
        break;
      case 9:
        fretColor = Color.AQUAMARINE;
        break;
      case 10:
        fretColor = Color.VIOLET;
        break;
      case 11:
        fretColor = Color.BLUEVIOLET;
        break;
      case 12:
        fretColor = Color.MEDIUMPURPLE;
        break;
      default:
        fretColor = Color.LIGHTBLUE;
        break;
    }
    return fretColor;
  }

  /**
   * Returns an int value where the note will be displayed on the x axis (in pixels)
   * 
   * @param onset
   * @return
   */
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
    double shift = -30;
    double pixelsBetweenStrings = boxHeight / 6;

    return shift + pixelsBetweenStrings * guitarString;
  }

  public Timer scrollTimer;
  private long lastPosition = 0;

  /**
   * Changes the state of the playback engine. If the engine is playing then it will stop and update
   * all variables. If it is not playing it will start the playback.
   * 
   * @param e
   */
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

  /**
   * Prints where the marker stopped
   */
  public void stopTimer() {
    System.out.println(scrollTimer.hashCode());
    System.out.println(lnMarker.hashCode());
    scrollTimer.cancel();
  }
}
